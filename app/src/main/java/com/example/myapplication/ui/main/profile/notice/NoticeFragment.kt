package com.example.myapplication.ui.main.profile.notice

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNoticeBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    override fun init() {
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val noticeAdapter = NoticeAdapter()

            val NoticeDataList: ArrayList<NoticeData> = ArrayList()
            NoticeDataList.add(NoticeData("1", "title1"))
            NoticeDataList.add(NoticeData("2", "title2"))
            NoticeDataList.add(NoticeData("3", "title3"))
            NoticeDataList.add(NoticeData("4", "title4"))
            NoticeDataList.add(NoticeData("5", "title5"))

            noticeRecyclerview.adapter = noticeAdapter
            noticeRecyclerview.layoutManager = LinearLayoutManager(context)
            noticeRecyclerview.addItemDecoration(OrderListDivider(30f,30f,4f,0f, Color.GRAY))
            noticeAdapter.userList = NoticeDataList
            noticeAdapter.notifyDataSetChanged()
        }
    }
}

