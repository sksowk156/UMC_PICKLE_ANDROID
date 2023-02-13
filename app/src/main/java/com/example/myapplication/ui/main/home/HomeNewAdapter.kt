package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding
import com.example.myapplication.db.remote.model.DressOverviewDto
import com.example.myapplication.ui.ItemCardClickInterface

class HomeNewAdapter(clicklistener: ItemCardClickInterface) :
    ListAdapter<DressOverviewDto, HomeNewAdapter.MyViewHolder>(HomeNewDiffUtil) {

    var clicklistener: ItemCardClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(new_clothes: DressOverviewDto) {
            var temp_favorite : Boolean = new_clothes?.dress_like!!

            if (temp_favorite == false) {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(binding.homecardImageviewFavorite) //보여줄 위치
            } else {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(binding.homecardImageviewFavorite) //보여줄 위치
            }

            Glide.with(this.itemView)
                .load(new_clothes.dress_default_img) //이미지
                .into(binding.homecardImageviewImage) //보여줄 위치

            binding.homecardTextviewStorename.text = new_clothes.store_name
            binding.homecardTextviewClothename.text = new_clothes.dress_name
            binding.homecardTextviewClotheprice.text = new_clothes.dress_price

            binding.homecardCardviewFrame.setOnClickListener {
                clicklistener.onItemClothImageClick(new_clothes.dress_id, absoluteAdapterPosition)
            }

            binding.homecardTextviewStorename.setOnClickListener {
                clicklistener.onItemStoreNameClick(new_clothes.store_id, absoluteAdapterPosition)
            }

            binding.homecardImageviewFavorite.setOnClickListener {
                clicklistener.onItemClothFavoriteClick(temp_favorite, new_clothes.dress_id, binding.homecardImageviewFavorite, absoluteAdapterPosition)
//                temp_favorite = changeFavoriteImage(this@MyViewHolder.itemView, temp_favorite!!, binding.homecardImageviewFavorite)
            }
        }
    }

    private fun changeFavoriteImage(view: View, like:Boolean, imageview: ImageView) : Boolean{
        if (like == true) {
            //화면에 보여주기
            Glide.with(view)
                .load(R.drawable.icon_favorite_whiteline) //이미지
                .into(imageview) //보여줄 위치
            return false
        } else {
            //화면에 보여주기
            Glide.with(view)
                .load(R.drawable.icon_favorite_filledpink) //이미지
                .into(imageview) //보여줄 위치
            return true
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
        return (oldItem.dress_id == newItem.dress_id&& oldItem.dress_like == newItem.dress_like)
    }

    override fun areContentsTheSame(oldItem: DressOverviewDto, newItem: DressOverviewDto): Boolean {
        return oldItem == newItem
    }
}