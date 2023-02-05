package com.example.myapplication.ui.main.profile.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNoticeRecyclerBinding

class NoticeAdapter(clickListener: NoticeClickListener) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    var userList: ArrayList<NoticeData>?= null

    interface NoticeClickListener {
        fun onItemBackgroundClick(view: View, position: Int)
    }
    var clicklistener: NoticeClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNoticeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemNoticeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: NoticeData?) {
            with(binding) {
                noticeItemTextviewName.text = user?.notice_name
                noticeItemTextviewDate.text

                binding.noticeLayout.setOnClickListener{
                    clicklistener.onItemBackgroundClick(it,absoluteAdapterPosition)
                }
            }
        }
    }
}