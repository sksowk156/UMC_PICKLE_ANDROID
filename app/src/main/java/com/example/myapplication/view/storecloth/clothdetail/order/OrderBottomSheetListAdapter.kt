package com.example.myapplication.view.storecloth.clothdetail.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemClothcountRecyclerBinding
import com.example.myapplication.data.remote.model.order.ClothOrderData

class OrderBottomSheetListAdapter (clicklistener: OrderClickListener) :
    ListAdapter<ClothOrderData, OrderBottomSheetListAdapter.MyViewHolder>(OrderDiffUtil) {

    interface OrderClickListener {
        fun onItemPlusClick(_clothorderdata: ClothOrderData, position: Int)
        fun onItemMinusClick(_clothorderdata: ClothOrderData, position: Int)
        fun onItemCloseClick(_clothorderdata: ClothOrderData, position: Int)
    }

    var clicklistener: OrderClickListener = clicklistener

    inner class MyViewHolder(val binding: ItemClothcountRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothorderdata: ClothOrderData) {
            binding.clothcountItemTextviewPlus.setOnClickListener {
                clicklistener.onItemPlusClick(clothorderdata, absoluteAdapterPosition)
                binding.clothcountItemTextviewCount.text = clothorderdata.count.toString()
                binding.clothcountItemTextviewPrice.text = (clothorderdata.clothPrice * clothorderdata.count).toString()+"원"
            }

            binding.clothcountItemImageviewMinus.setOnClickListener {
                clicklistener.onItemMinusClick(clothorderdata, absoluteAdapterPosition)
                binding.clothcountItemTextviewCount.text = clothorderdata.count.toString()
                binding.clothcountItemTextviewPrice.text = (clothorderdata.clothPrice * clothorderdata.count).toString()+"원"
            }

            binding.clothcountItemImageviewClose.setOnClickListener {
                clicklistener.onItemCloseClick(clothorderdata, absoluteAdapterPosition)
            }

            binding.clothcountItemTextviewColorsize.text = "${clothorderdata.color} / ${clothorderdata.size}"
            binding.clothcountItemTextviewCount.text = clothorderdata.count.toString()
            binding.clothcountItemTextviewPrice.text = (clothorderdata.clothPrice*clothorderdata.count).toString()+"원"
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