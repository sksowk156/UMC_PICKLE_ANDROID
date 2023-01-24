package com.example.myapplication.ui.main.profile.inquiry

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInquiryBinding
import com.example.myapplication.ui.main.BaseFragment

class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry) {
    override fun init() {
        hideBottomNavigation(true)
    }


}