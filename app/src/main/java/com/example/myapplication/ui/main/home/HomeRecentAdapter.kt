package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding
import com.example.myapplication.db.remote.model.RecentView
import com.example.myapplication.ui.main.ItemClickInterface

class HomeRecentAdapter(clicklistener: ItemClickInterface) :
   ListAdapter<RecentView, HomeRecentAdapter.MyViewHolder>(HomeRecentDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recent_clothes: RecentView) {
//            if (recent_clothes?.좋아요 == false) {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_whiteline) //이미지
//                    .into(binding.homecardImagebuttonFavorite) //보여줄 위치
//            } else {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                    .into(binding.homecardImagebuttonFavorite) //보여줄 위치
//            }

            Glide.with(this.itemView)
                .load(recent_clothes.dress_default_img) //이미지
                .into(binding.homecardImageviewImage) //보여줄 위치

            binding.homecardTextviewStorename.text = recent_clothes.store_name
            binding.homecardTextviewClothename.text = recent_clothes.dress_name
            binding.homecardTextviewClotheprice.text = recent_clothes.dress_price

            binding.homecardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(recent_clothes.dress_id , absoluteAdapterPosition)
            }

            binding.homecardTextviewStorename.setOnClickListener {
                clicklistener.onItemStoreNameClick(recent_clothes.store_id, absoluteAdapterPosition)
            }
//
//            binding.homecardImagebuttonFavorite.setOnClickListener{
//                clicklistener.onItemFavoriteClick(it,absoluteAdapterPosition)
//            }
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

object HomeRecentDiffUtil : DiffUtil.ItemCallback<RecentView>() {
    override fun areItemsTheSame(oldItem: RecentView, newItem: RecentView): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: RecentView, newItem: RecentView): Boolean {
        return oldItem == newItem
    }
}