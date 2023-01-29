package com.example.myapplication.ui.main.profile.orderstatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderstatusRecyclerBinding

class OrderstatusAdapter : RecyclerView.Adapter<OrderstatusAdapter.ViewHolder>() {

    var userList: ArrayList<ClotheData>?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderstatusRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)

        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemOrderstatusRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: ClotheData?) {
            with(binding) {
                orderstatusTextviewDate.text
                orderstatusImageProduct.drawable
                orderstatusTextviewMarketname.text
                orderstatusTextviewClothename.text
                orderstatusTextviewClotheprice.text
            }
        }
    }
}