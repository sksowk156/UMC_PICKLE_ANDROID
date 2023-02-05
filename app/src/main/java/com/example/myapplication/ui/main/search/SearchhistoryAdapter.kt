package com.example.myapplication.ui.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemSearchhistoryRecyclerBinding

class SearchhistoryAdapter(val clicklistener: ItemClickListener) :
    RecyclerView.Adapter<SearchhistoryAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onTextItemClick(view: View, position: Int)
        fun onImageItemClick(view: View, position: Int)
    }

    var userList: ArrayList<SearchHistroyData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchhistoryRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)

        holder.binding.searchhistoryLayoutDetail.setOnClickListener {
            clicklistener.onTextItemClick(it, position)
        }

        holder.binding.searchhistoryImageDelete.setOnClickListener {
            clicklistener.onImageItemClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return userList?.size?.let { Math.min(it, 10) }?: 0
    }

    inner class ViewHolder(val binding: ItemSearchhistoryRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: SearchHistroyData?) {
            with(binding) {
                searchhistoryTextviewHistory.text = user?.searchhistory
            }
        }
    }
}