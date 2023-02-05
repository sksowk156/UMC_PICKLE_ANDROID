package com.example.myapplication.ui.main.profile.orderstatus.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderstatusdetailRecyclerBinding
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData

class OrderstatusDetailAdapter() : RecyclerView.Adapter<OrderstatusDetailAdapter.ViewHolder>() {

    var userList: ArrayList<OrderedClotheData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderstatusdetailRecyclerBinding.inflate(
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

    inner class ViewHolder(val binding: ItemOrderstatusdetailRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: OrderedClotheData?) {
            with(binding) {
                orderstatusdetailImageProduct
                orderstatusdetailTextviewClothename.text = user?.orderclothename
                orderstatusdetailTextviewClothecolorsize.text = "${user?.orderclothesize} / ${user?.orderclothesize}"
                orderstatusdetailTextviewClotheprice.text = user?.orderclotheprice
            }
        }
    }
}