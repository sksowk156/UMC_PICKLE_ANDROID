package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding
import com.example.myapplication.db.remote.model.DressOverviewDto
import com.example.myapplication.ui.main.ItemClickInterface

class HomeNewAdapter(clicklistener: ItemClickInterface) :
    ListAdapter<DressOverviewDto, HomeNewAdapter.MyViewHolder>(HomeNewDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(new_clothes: DressOverviewDto) {
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
                .load(new_clothes.dress_default_img) //이미지
                .into(binding.homecardImageviewImage) //보여줄 위치

            binding.homecardTextviewStorename.text = new_clothes.store_name
            binding.homecardTextviewClothename.text = new_clothes.dress_name
            binding.homecardTextviewClotheprice.text = new_clothes.dress_price

            binding.homecardCardviewFrame.setOnClickListener {
                clicklistener.onItemImageClick(new_clothes.dress_id, absoluteAdapterPosition)
            }

            binding.homecardTextviewStorename.setOnClickListener {
                clicklistener.onItemStoreNameClick(new_clothes.store_id, absoluteAdapterPosition)
            }

//            binding.homecardImagebuttonFavorite.setOnClickListener {
//                clicklistener.onItemFavoriteClick(it, absoluteAdapterPosition)
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

object HomeNewDiffUtil : DiffUtil.ItemCallback<DressOverviewDto>() {
    override fun areItemsTheSame(oldItem: DressOverviewDto, newItem: DressOverviewDto): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: DressOverviewDto, newItem: DressOverviewDto): Boolean {
        return oldItem == newItem
    }
}