package com.example.myapplication.ui.storecloth.storedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import com.example.myapplication.db.remote.model.DressBriefInStoreDTO
import com.example.myapplication.ui.ItemCardClickInterface

class StoreDetailAdapter(clicklistener: ItemCardClickInterface) :
    ListAdapter<DressBriefInStoreDTO, StoreDetailAdapter.MyViewHolder>(StoreDetailDiffUtil) {

    var clicklistener: ItemCardClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dressBriefInStoreDTO: DressBriefInStoreDTO) {
//            if (dressBriefInStoreDTO?.좋아요 == false) {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_whiteline) //이미지
//                    .into(binding.cardImageviewFavorite) //보여줄 위치
//            } else {
//                //화면에 보여주기
//                Glide.with(this@MyViewHolder.itemView)
//                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                    .into(binding.cardImageviewFavorite) //보여줄 위치
//            }

//            binding.cardImageviewFavorite.setOnClickListener{
//                clicklistener.onItemClothFavoriteClick(dressBriefInStoreDTO.좋아요, dressBriefInStoreDTO.dress_id,it,absoulteAdapterPosition)
//            }

            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemClothImageClick(dressBriefInStoreDTO.dress_id, absoluteAdapterPosition)
            }

            // 매장 상세페이지에서 매장 이름이 들어갈 필요가 없음
//            binding.cardTextviewStorename.setOnClickListener {
//                clicklistener.onItemStoreNameClick(dressBriefInStoreDTO.dress_id, absoluteAdapterPosition)
//            }


            Glide.with(this.itemView)
                .load(dressBriefInStoreDTO.dress_image_url) //이미지
                .into(binding.cardImageviewImage) //보여줄 위치

            binding.cardTextviewClothename.text = dressBriefInStoreDTO.dress_name
            binding.cardTextviewClotheprice.text = dressBriefInStoreDTO.dress_price
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

object StoreDetailDiffUtil : DiffUtil.ItemCallback<DressBriefInStoreDTO>() {
    override fun areItemsTheSame(oldItem: DressBriefInStoreDTO, newItem: DressBriefInStoreDTO): Boolean {
        return oldItem.dress_id == newItem.dress_id
    }

    override fun areContentsTheSame(oldItem: DressBriefInStoreDTO, newItem: DressBriefInStoreDTO): Boolean {
        return oldItem == newItem
    }
}