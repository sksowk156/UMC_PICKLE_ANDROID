package com.example.myapplication.ui.main.home.recent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.Clothes

class CardViewAdapter(clicklistener: ItemClickInterface) :
    ListAdapter<Clothes, CardViewAdapter.MyViewHolder>(CardViewDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothes: Clothes) {
            if (clothes?.like == false) {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(binding.cardImagebuttonFavorite) //보여줄 위치
            } else {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(binding.cardImagebuttonFavorite) //보여줄 위치
            }

            binding.cardImagebuttonFavorite.setOnClickListener{
                clicklistener.onItemFavoriteClick(it,absoluteAdapterPosition)
            }

            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(it, absoluteAdapterPosition)
            }

            binding.cardTextviewStorename.setOnClickListener {
                clicklistener.onItemMarketNameClick(it, absoluteAdapterPosition)
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