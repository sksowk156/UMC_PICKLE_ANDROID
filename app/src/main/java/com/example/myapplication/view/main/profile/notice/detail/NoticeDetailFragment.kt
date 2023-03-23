package com.example.myapplication.view.main.profile.notice.detail

import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentNoticeDetailBinding

class NoticeDetailFragment : BaseFragment<FragmentNoticeDetailBinding>(R.layout.fragment_notice_detail) {
    override fun init() {
        hideBottomNavigation(true)
        initAppbar(binding.noticedetailToolbar, "", true, false)
    }

}