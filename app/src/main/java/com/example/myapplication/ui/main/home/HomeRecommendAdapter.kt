package com.example.myapplication.ui.main.home.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import com.example.myapplication.db.remote.model.RecDresse
import com.example.myapplication.ui.main.ItemClickInterface

class HomeRecommendAdapter(clicklistener: ItemClickInterface) :
    ListAdapter<RecDresse, HomeRecommendAdapter.MyViewHolder>(HomeRecommendDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommend_clothes: RecDresse) {
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
                .load(recommend_clothes.dress_default_img) //이미지
                .into(binding.cardImageviewImage) //보여줄 위치

            binding.cardTextviewStorename.text = recommend_clothes.store_name
            binding.cardTextviewClothename.text = recommend_clothes.dress_name
            binding.cardTextviewClotheprice.text = recommend_clothes.dress_price

            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(recommend_clothes.dress_id, absoluteAdapterPosition)
            }

            binding.cardTextviewStorename.setOnClickListener {
                clicklistener.onItemStoreNameClick(recommend_clothes.store_id, absoluteAdapterPosition)
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

object HomeRecommendDiffUtil : DiffUtil.ItemCallback<RecDresse>() {
    override fun areItemsTheSame(oldItem: RecDresse, newItem: RecDresse): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: RecDresse, newItem: RecDresse): Boolean {
        return oldItem == newItem
    }
}