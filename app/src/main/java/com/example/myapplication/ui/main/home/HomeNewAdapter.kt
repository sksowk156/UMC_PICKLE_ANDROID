package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemHomecardRecyclerBinding
import com.example.myapplication.db.remote.model.NewDresse
import com.example.myapplication.ui.main.ItemClickInterface

class HomeNewAdapter(clicklistener: ItemClickInterface) :
    ListAdapter<NewDresse, HomeNewAdapter.MyViewHolder>(HomeNewDiffUtil) {

    var clicklistener: ItemClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemHomecardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(new_clothes: NewDresse) {
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

//            binding.homecardCardviewFrame.setOnClickListener {
//                clicklistener.onItemImageClick(it, absoluteAdapterPosition)
//            }
//
//            binding.homecardTextviewStorename.setOnClickListener {
//                clicklistener.onItemStoreNameClick(it, absoluteAdapterPosition)
//            }
//
//            binding.homecardImagebuttonFavorite.setOnClickListener{
//                clicklistener.onItemFavoriteClick(it,absoluteAdapterPosition)
//            }

            Glide.with(this.itemView)
                .load(new_clothes.dress_default_img) //이미지
                .into(binding.homecardImageviewImage) //보여줄 위치

            binding.homecardTextviewStorename.text = new_clothes.store_name
            binding.homecardTextviewClothename.text = new_clothes.dress_name
            binding.homecardTextviewClotheprice.text = new_clothes.dress_price
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

object HomeNewDiffUtil : DiffUtil.ItemCallback<NewDresse>() {
    override fun areItemsTheSame(oldItem: NewDresse, newItem: NewDresse): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: NewDresse, newItem: NewDresse): Boolean {
        return oldItem == newItem
    }
}