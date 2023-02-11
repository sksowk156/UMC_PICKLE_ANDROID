package com.example.myapplication.ui.main.favorite.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.db.remote.model.DressOverviewDto
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter

class FavoriteItemAdapter(clicklistener: ItemClickInterface) :
    ListAdapter<DressLikeDto, FavoriteItemAdapter.MyViewHolder>(FavoriteItemDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(like_cloth: DressLikeDto) {
//            if (RecDresse?.좋아요 == false) {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_whiteline) //이미지
//                    .into(binding.cardImagebuttonFavorite) //보여줄 위치
//            } else {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                    .into(binding.cardImagebuttonFavorite) //보여줄 위치
//            }

            Glide.with(this.itemView)
                .load(like_cloth.image) //이미지
                .into(binding.cardImageviewImage) //보여줄 위치

            binding.cardTextviewClothename.text = like_cloth.name
            binding.cardTextviewClotheprice.text = like_cloth.price

            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(like_cloth.dress_id, absoluteAdapterPosition)
            }

//            binding.cardImagebuttonFavorite.setOnClickListener {
//                clicklistener.onItemFavoriteClick(recommend_clothes.좋아요, absoluteAdapterPosition)
//            }
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

object FavoriteItemDiffUtil : DiffUtil.ItemCallback<DressLikeDto>() {
    override fun areItemsTheSame(oldItem: DressLikeDto, newItem: DressLikeDto): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: DressLikeDto, newItem: DressLikeDto): Boolean {
        return oldItem == newItem
    }
}