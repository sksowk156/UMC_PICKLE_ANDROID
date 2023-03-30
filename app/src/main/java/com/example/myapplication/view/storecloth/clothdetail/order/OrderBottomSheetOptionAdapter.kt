package com.example.myapplication.view.storecloth.clothdetail.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.model.DressOptionDetailDto
import com.example.myapplication.databinding.ItemOrderbottomsheetOptionBinding

class OrderBottomSheetOptionAdapter(clickListener: OptionClickListener) :
    RecyclerView.Adapter<OrderBottomSheetOptionAdapter.ViewHolder>() {

    var userList: MutableList<DressOptionDetailDto>? = null

    interface OptionClickListener {
        fun onItemBackgroundClick(view: View, position: Int, id: Int)
    }

    var clicklistener: OptionClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderbottomsheetOptionBinding.inflate(
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

    inner class ViewHolder(val binding: ItemOrderbottomsheetOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: DressOptionDetailDto?) {
            with(binding) {
                orderbottomsheetoptionTextviewOption.text = user?.dress_option_detail_name

                binding.orderbottomsheetoptionLayout.setOnClickListener {
                    clicklistener.onItemBackgroundClick(
                        it, absoluteAdapterPosition,
                        user?.dress_option_detail_id!!
                    )
                }
            }
        }
    }
}