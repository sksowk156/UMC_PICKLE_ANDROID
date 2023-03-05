package com.example.myapplication.view.main.profile.inquiry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemInquiryRecyclerBinding

class InquiryAdapter : RecyclerView.Adapter<InquiryAdapter.ViewHolder>() {

    var userList: ArrayList<InquiryData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInquiryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position)

        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemInquiryRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: InquiryData?) {
            binding.inquiryItemTextviewTitle.text = user?.inquiry_title
            binding.inquiryItemTextviewAnswer.text = user?.inquiry_answer
            val layoutExpand = binding.inquiryItemInnerlayout2
            binding.inquiryItemInnerlayout1.setOnClickListener {
                // 1
                val show = toggleLayout(!layoutExpand.isVisible, it.findViewById(binding.inquiryItemImage1.id), layoutExpand)
                layoutExpand.isVisible = show
            }
        }

        private fun toggleLayout(
            isExpanded: Boolean,
            view: View,
            layoutExpand: LinearLayout
        ): Boolean {
            // 2
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }

}