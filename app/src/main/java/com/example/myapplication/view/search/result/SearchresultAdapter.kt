package com.example.myapplication.view.search.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.DressOverviewDto
import com.example.myapplication.databinding.ItemCardRecyclerBinding
import com.example.myapplication.data.remote.model.DressSearchResultDto
import com.example.myapplication.view.ItemCardClickInterface
import com.example.myapplication.view.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.view.main.home.recent.HomeRecommendDiffUtil

//class SearchresultAdapter(clickInterface: ItemCardClickInterface): RecyclerView.Adapter<SearchresultAdapter.MyViewHolder>(){
//
//    private var itemList = ArrayList<DressSearchResultDto>()
//
//    fun updateData(new_data : ArrayList<DressSearchResultDto>){
//        itemList = new_data
//    }
//    fun deleteData(){
//        itemList.clear()
//    }
//
//    private var clicklistener: ItemCardClickInterface = clickInterface
//
//    inner class MyViewHolder(val binding: ItemCardRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(recent_clothes: DressSearchResultDto) {
//            if (recent_clothes.is_liked == false) {
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
//
//            Glide.with(this.itemView)
//                .load(recent_clothes.dress_image_url) //이미지
//                .into(binding.cardImageviewImage) //보여줄 위치
//
//            binding.cardTextviewStorename.text = recent_clothes.store_name
//            binding.cardTextviewClothename.text = recent_clothes.dress_name
//            binding.cardTextviewClotheprice.text = recent_clothes.dress_price
//
//            binding.cardCardviewFrame.setOnClickListener {
//                clicklistener.onItemClothImageClick(
//                    recent_clothes.dress_id,
//                    absoluteAdapterPosition
//                )
//            }
//
//            binding.cardTextviewStorename.setOnClickListener {
//                clicklistener.onItemStoreNameClick(recent_clothes.store_id, absoluteAdapterPosition)
//            }
//
//            binding.cardImageviewFavorite.setOnClickListener {
//                clicklistener.onItemClothFavoriteClick(
//                    recent_clothes.is_liked!!,
//                    recent_clothes.dress_id,
//                    binding.cardImageviewFavorite,
//                    absoluteAdapterPosition
//                )
//            }
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val listItemBinding = ItemCardRecyclerBinding.inflate(inflater, parent, false)
//        return MyViewHolder(listItemBinding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(itemList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//}
class SearchresultAdapter(clicklistener: ItemCardClickInterface) :
    ListAdapter<DressSearchResultDto, SearchresultAdapter.MyViewHolder>(SearchresultDiffUtil) {

    var clicklistener: ItemCardClickInterface = clicklistener

    inner class MyViewHolder(val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dressResult: DressSearchResultDto) {
            if (dressResult.is_liked == false) {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(binding.cardImageviewFavorite) //보여줄 위치
            } else {
                //화면에 보여주기
                Glide.with(this@MyViewHolder.itemView)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(binding.cardImageviewFavorite) //보여줄 위치
            }

            Glide.with(this.itemView)
                .load(dressResult.dress_image_url) //이미지
                .into(binding.cardImageviewImage) //보여줄 위치

            binding.cardTextviewStorename.text = dressResult.store_name
            binding.cardTextviewClothename.text = dressResult.dress_name
            binding.cardTextviewClotheprice.text = dressResult.dress_price

            binding.cardCardviewFrame.setOnClickListener {
                clicklistener.onItemClothImageClick(dressResult.dress_id, absoluteAdapterPosition)
            }

            binding.cardTextviewStorename.setOnClickListener {
                clicklistener.onItemStoreNameClick(dressResult.store_id, absoluteAdapterPosition)
            }

            binding.cardImageviewFavorite.setOnClickListener {
                if ( dressResult?.is_liked!! == true) {
                    //화면에 보여주기
                    Glide.with(this@MyViewHolder.itemView)
                        .load(R.drawable.icon_favorite_whiteline) //이미지
                        .into(binding.cardImageviewFavorite) //보여줄 위치
                    dressResult?.is_liked = false
                } else {
                    //화면에 보여주기
                    Glide.with(this@MyViewHolder.itemView)
                        .load(R.drawable.icon_favorite_filledpink) //이미지
                        .into(binding.cardImageviewFavorite) //보여줄 위치
                    dressResult?.is_liked = true
                }
                clicklistener.onItemClothFavoriteClick(dressResult.is_liked!!, dressResult.dress_id, binding.cardImageviewFavorite, absoluteAdapterPosition)
            }
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

object SearchresultDiffUtil : DiffUtil.ItemCallback<DressSearchResultDto>() {
    override fun areItemsTheSame(oldItem: DressSearchResultDto, newItem: DressSearchResultDto): Boolean {
        return (oldItem=== newItem)
    }

    override fun areContentsTheSame(oldItem: DressSearchResultDto, newItem: DressSearchResultDto): Boolean {
        return  (oldItem.equals(newItem))
    }
}