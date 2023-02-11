package com.example.myapplication.ui.storecloth.clothdetail.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemClothcountRecyclerBinding
import com.example.myapplication.db.remote.model.order.ClothOrderData

class OrderBottomSheetAdapter (clicklistener: OrderClickListener) :
    ListAdapter<ClothOrderData, OrderBottomSheetAdapter.MyViewHolder>(OrderDiffUtil) {

    interface OrderClickListener {
        fun onItemPlusClick(_clothorderdata: ClothOrderData, position: Int)
        fun onItemMinusClick(_clothorderdatat: ClothOrderData, position: Int)
        fun onItemCloseClick(_clothorderdatat: ClothOrderData, position: Int)
    }

    var clicklistener: OrderClickListener = clicklistener

    inner class MyViewHolder(val binding: ItemClothcountRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothorderdatat: ClothOrderData) {
            binding.clothcountItemTextviewPlus.setOnClickListener {
                clicklistener.onItemPlusClick(clothorderdatat, absoluteAdapterPosition)
                binding.clothcountItemTextviewCount.text = clothorderdatat.count.toString()
            }

            binding.clothcountItemImageviewMinus.setOnClickListener {
                clicklistener.onItemMinusClick(clothorderdatat, absoluteAdapterPosition)
                binding.clothcountItemTextviewCount.text = clothorderdatat.count.toString()
            }

            binding.clothcountItemImageviewClose.setOnClickListener {
                clicklistener.onItemCloseClick(clothorderdatat, absoluteAdapterPosition)
            }

            binding.clothcountItemTextviewColorsize.text = "${clothorderdatat.color} / ${clothorderdatat.size}"
            binding.clothcountItemTextviewCount.text = clothorderdatat.count.toString()
            binding.clothcountItemTextviewPrice.text = (clothorderdatat.clothPrice*clothorderdatat.count).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemClothcountRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object OrderDiffUtil : DiffUtil.ItemCallback<ClothOrderData>() {
    override fun areItemsTheSame(oldItem: ClothOrderData, newItem: ClothOrderData): Boolean {
        return oldItem.hashCode()  == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: ClothOrderData, newItem: ClothOrderData): Boolean {
        return oldItem == newItem
    }
}