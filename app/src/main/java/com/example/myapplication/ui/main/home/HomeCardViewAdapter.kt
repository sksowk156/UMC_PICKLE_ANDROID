package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding
import com.example.myapplication.ui.main.ItemClickInterface

class HomeCardViewAdapter(clicklistener: ItemClickInterface) :
   ListAdapter<Clothes, HomeCardViewAdapter.MyViewHolder>(HomeCardViewDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothes: Clothes) {
            if (clothes?.like == false) {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(binding.homecardImagebuttonFavorite) //보여줄 위치
            } else {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(binding.homecardImagebuttonFavorite) //보여줄 위치
            }

            binding.homecardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(it, absoluteAdapterPosition)
            }

            binding.homecardTextviewStorename.setOnClickListener {
                clicklistener.onItemMarketNameClick(it, absoluteAdapterPosition)
            }

            binding.homecardImagebuttonFavorite.setOnClickListener{
                clicklistener.onItemFavoriteClick(it,absoluteAdapterPosition)
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