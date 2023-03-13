package com.example.myapplication.view.main.profile.notice

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNoticeBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.profile.notice.detail.NoticeDetailFragment
import com.example.myapplication.view.main.profile.orderstatus.OrderListDivider


class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    override fun init() {
        hideBottomNavigation(true)
        initAppbar(binding.noticeToolbar, "공지사항", true, false)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val noticeAdapter = NoticeAdapter(clickListener = (object : NoticeAdapter.NoticeClickListener{
                override fun onItemBackgroundClick(view: View, position: Int) {
                    // 공지 상세 페이지로 이동
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.profileblank_layout, NoticeDetailFragment() ,"noticedetail")
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }))

            val NoticeDataList: ArrayList<NoticeData> = ArrayList()
            NoticeDataList.add(NoticeData("1", "title1"))
            NoticeDataList.add(NoticeData("2", "title2"))
            NoticeDataList.add(NoticeData("3", "title3"))
            NoticeDataList.add(NoticeData("4", "title4"))
            NoticeDataList.add(NoticeData("5", "title5"))

            noticeRecyclerview.adapter = noticeAdapter
            noticeRecyclerview.layoutManager = LinearLayoutManager(context)
            noticeRecyclerview.addItemDecoration(OrderListDivider(30f,30f,4f,0f, Color.parseColor("#E1E1E1")))
            noticeAdapter.userList = NoticeDataList
            noticeAdapter.notifyDataSetChanged()
        }
    }
}

