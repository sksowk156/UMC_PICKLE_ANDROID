package com.example.myapplication.ui.main.profile.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNoticeBinding
import com.example.myapplication.ui.main.BaseFragment

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    override fun init() {
        hideBottomNavigation(true)
    }
}

