package com.example.myapplication.ui.main.location.around

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAroundRecyclerBinding
import com.example.myapplication.ui.main.location.MapAroundData

class AroundAdapter : RecyclerView.Adapter<AroundAdapter.ViewHolder>() {

    var userList: ArrayList<MapAroundData>?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAroundRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)

        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemAroundRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: MapAroundData?) {
            with(binding) {
                marketName.text = user?.market_around_name.toString()
                marketAddress.text = user?.market_around_address.toString()
                marketOperationhours.text = user?.market_around_operationhours.toString()
            }
        }
    }
}
