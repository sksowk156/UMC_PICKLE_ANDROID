package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding

class HomeCardViewAdapter(clicklistener: ClothesClickListener) :
   ListAdapter<Clothes, HomeCardViewAdapter.MyViewHolder>(HomeCardViewDiffUtil) {

    interface ClothesClickListener {
        fun onItemImageClick(view: View, position: Int)
        fun onItemMarketNameClick(view: View, position: Int)
        fun onItemButtonClick(view: View,position: Int)
    }

    var clicklistener: ClothesClickListener = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothes: Clothes) {
            binding.homecardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(it, absoluteAdapterPosition)
            }

            binding.homecardTextviewStorename.setOnClickListener {
                clicklistener.onItemMarketNameClick(it, absoluteAdapterPosition)
            }

            binding.homecardImagebuttonFavorite.setOnClickListener{
                clicklistener.onItemButtonClick(it,absoluteAdapterPosition)
            }
            binding.homecardImageviewImage.setImageResource(clothes.image)
            binding.homecardTextviewStorename.text = clothes.store
            binding.homecardTextviewClothename.text = clothes.name
            binding.homecardTextviewClotheprice.text = clothes.price.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHomecardRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object HomeCardViewDiffUtil : DiffUtil.ItemCallback<Clothes>() {
    override fun areItemsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
        return oldItem == newItem
    }
}