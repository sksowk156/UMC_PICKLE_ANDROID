package com.example.myapplication.ui.main.location.around

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAroundRecyclerBinding
import com.example.myapplication.db.remote.model.MapModel
import com.example.myapplication.db.remote.model.MapModelItem
import com.example.myapplication.db.remote.model.StoreDetailData
import com.example.myapplication.ui.main.home.recent.CardViewAdapter
import kotlinx.android.synthetic.main.item_around_recycler.*

class AroundAdapter(clicklistener: AroundAdapter.ClothesClickListener) :
    RecyclerView.Adapter<AroundAdapter.ViewHolder>() {

    var userList: ArrayList<StoreDetailData>? = null

    var clicklistener: AroundAdapter.ClothesClickListener = clicklistener

    interface ClothesClickListener {
        fun onItemMarketFavoriteClick(view: View, position: Int)
        fun onItemMarketLayoutClick(view: View, position: Int)
    }

    fun updatedata(data : ArrayList<StoreDetailData>){
        userList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAroundRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)

        holder.setUser(user, position)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemAroundRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: StoreDetailData?, position: Int) {
            with(binding) {
//                if (user?. == false) {
//                    //화면에 보여주기
//                    Glide.with(this@ViewHolder.itemView)
//                        .load(R.drawable.icon_favorite_line) //이미지
//                        .into(marketFavorite) //보여줄 위치
//                } else {
//                    //화면에 보여주기
//                    Glide.with(this@ViewHolder.itemView)
//                        .load(R.drawable.icon_favorite_filledpink) //이미지
//                        .into(marketFavorite) //보여줄 위치
//                }
                marketName.text = user?.store_name.toString()
                marketAddress.text = user?.store_address.toString()
                marketOperationhours.text = user?.hours_of_operation.toString()

                marketLayout.setOnClickListener {
                    clicklistener.onItemMarketLayoutClick(it, absoluteAdapterPosition)
                    notifyDataSetChanged()
                }
                marketFavorite.setOnClickListener {
                    clicklistener.onItemMarketFavoriteClick(it, absoluteAdapterPosition)
                    notifyDataSetChanged()

                }
            }
        }
    }
}
