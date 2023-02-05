package com.example.myapplication.ui.main.home

import android.app.ActionBar
import android.util.Log
import android.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.ui.base.BaseFragment

class HomeBaseFragment : BaseFragment<FragmentHomeBaseBinding>(R.layout.fragment_home_base) {

    override fun init() {
        // 초기 화면
        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_base_layout, HomeFragment(), "home")
            .commitAllowingStateLoss()

        initAppbar(binding.homeBaseToolbarcontent, binding.homeBaseToolbar, "홈")
        changeAppbar()
    }

    fun changeAppbar() {
        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "recent" -> {
                    initSubAppbar("최근 본 상품", true, false)
                }
                "new" -> {
                    initSubAppbar("NEW", true, false)
                }
                "home" -> {
                    initSubAppbar("홈", false, true)
                }
            }
        }
    }

}