package com.example.myapplication.ui.main.profile.inquiry

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInquiryBinding
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.profile.ProfileBlankFragment

class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry) {
    override fun init() {
        hideBottomNavigation(true)
    }


}