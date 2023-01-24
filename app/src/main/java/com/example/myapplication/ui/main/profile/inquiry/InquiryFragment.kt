package com.example.myapplication.ui.main.profile.inquiry

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInquiryBinding
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.profile.ProfileBlankFragment
import com.example.myapplication.ui.main.profile.notice.NoticeAdapter
import com.example.myapplication.ui.main.profile.notice.NoticeData
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider

class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry) {
    override fun init() {
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val inquiryAdapter = InquiryAdapter()

            val InquiryDataList: ArrayList<InquiryData> = ArrayList()
            InquiryDataList.add(InquiryData("1", "title1"))
            InquiryDataList.add(InquiryData("2", "title2"))
            InquiryDataList.add(InquiryData("3", "title3"))
            InquiryDataList.add(InquiryData("4", "title4"))
            InquiryDataList.add(InquiryData("5", "title5"))

            inquiryRecyclerview.adapter = inquiryAdapter
            inquiryRecyclerview.layoutManager = LinearLayoutManager(context)
            inquiryRecyclerview.addItemDecoration(OrderListDivider(0f,0f,6f,0f, Color.TRANSPARENT))
            inquiryAdapter.userList = InquiryDataList
            inquiryAdapter.notifyDataSetChanged()
        }
    }
}