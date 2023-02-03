package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class CardViewAdapter(clicklistener: ClothesClickListener) :
   ListAdapter<Clothes, CardViewAdapter.MyViewHolder>(CardViewDiffUtil) {

    interface ClothesClickListener {
        fun onItemImageClick(view: View, position: Int)
        fun onItemMarketNameClick(view: View, position: Int)
        fun onItemButtonClick(view: View,position: Int)
    }

    var clicklistener: ClothesClickListener = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothes: Clothes) {
            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(it, absoluteAdapterPosition)
            }

            binding.cardTextviewStorename.setOnClickListener {
                clicklistener.onItemMarketNameClick(it, absoluteAdapterPosition)
            }

            binding.cardImagebuttonFavorite.setOnClickListener{
                clicklistener.onItemButtonClick(it,absoluteAdapterPosition)
            }
            binding.cardImageviewImage.setImageResource(clothes.image)
            binding.cardTextviewStorename.text = clothes.store
            binding.cardTextviewClothename.text = clothes.name
            binding.cardTextviewClotheprice.text = clothes.price.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCardRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object CardViewDiffUtil : DiffUtil.ItemCallback<Clothes>() {
    override fun areItemsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
        return oldItem == newItem
    }
}