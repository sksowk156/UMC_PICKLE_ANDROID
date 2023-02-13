package com.example.myapplication.ui.main.favorite.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAroundRecyclerBinding
import com.example.myapplication.db.remote.model.StoreLikeDto

class FavoriteStoreAdapter(clicklistener: ClothesClickListener) :
    ListAdapter<StoreLikeDto, FavoriteStoreAdapter.MyViewHolder>(FavoriteStoreDiffUtil) {

    interface ClothesClickListener {
        fun onItemMarketFavoriteClick(view: View, position: Int)
        fun onItemMarketLayoutClick(view: View, position: Int)
    }

    var clicklistener: ClothesClickListener = clicklistener

    inner class MyViewHolder(val binding: ItemAroundRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: StoreLikeDto) {
            with(binding) {

                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(marketFavorite) //보여줄 위치


                Glide.with(this@MyViewHolder.itemView)
                    .load(user?.imageUrl) //이미지
                    .into(marketImage) //보여줄 위치
                marketName.text = user?.name.toString()
                marketAddress.text = user?.address.toString()
                marketOperationhours.text = "${user?.store_open_day}, ${user?.open_time} ~ ${user?.close_time}"

                marketLayout.setOnClickListener {
                    clicklistener.onItemMarketLayoutClick(it, absoluteAdapterPosition)
                }
                marketFavorite.setOnClickListener {
                    clicklistener.onItemMarketFavoriteClick(it, absoluteAdapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemAroundRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object FavoriteStoreDiffUtil : DiffUtil.ItemCallback<StoreLikeDto>() {
    override fun areItemsTheSame(oldItem: StoreLikeDto, newItem: StoreLikeDto): Boolean {
        return oldItem.store_id  == newItem.store_id
    }

    override fun areContentsTheSame(oldItem: StoreLikeDto, newItem: StoreLikeDto): Boolean {
        return oldItem == newItem
    }
}