package com.example.myapplication.ui.main.profile.orderstatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderstatusRecyclerBinding

class OrderstatusAdapter(clicklistener: OrderstatusAdapter.OrderstatusClickListener) :
    RecyclerView.Adapter<OrderstatusAdapter.ViewHolder>() {

    var userList: ArrayList<OrderedClotheData>? = null

    interface OrderstatusClickListener {
        fun onItemImageClick(view: View, position: Int)
        fun onItemDetailClick(view: View, position: Int)
        fun onItemInnerlayoutClick(view: View, position: Int)
    }

    var clicklistener: OrderstatusAdapter.OrderstatusClickListener = clicklistener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderstatusRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)

        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemOrderstatusRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: OrderedClotheData?) {
            with(binding) {
                orderstatusInnerlayout.setOnClickListener {
                    clicklistener.onItemInnerlayoutClick(it, absoluteAdapterPosition)
                }

                orderstatusImageProduct.setOnClickListener {
                    clicklistener.onItemImageClick(it, absoluteAdapterPosition)
                }

                orderstatusTextviewDetail.setOnClickListener {
                    clicklistener.onItemDetailClick(it, absoluteAdapterPosition)
                }

                orderstatusTextviewDate.text = user?.orderdate
                orderstatusTextviewMarketname.text = user?.ordermarketname
                orderstatusTextviewClothename.text = user?.orderclothename
                orderstatusTextviewClotheprice.text = user?.orderclotheprice
            }
        }
    }
}