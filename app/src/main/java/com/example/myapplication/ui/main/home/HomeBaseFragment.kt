package com.example.myapplication.ui.main.home

import android.app.ActionBar
import android.util.Log
import android.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.ui.base.BaseFragment

class HomeBaseFragment : BaseFragment<FragmentHomeBaseBinding>(R.layout.fragment_home_base) {

    override fun savedatainit() {
        // 초기 화면
        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_base_layout, HomeFragment(), "home")
            .commitAllowingStateLoss()
    }

    override fun init() {
    }
}