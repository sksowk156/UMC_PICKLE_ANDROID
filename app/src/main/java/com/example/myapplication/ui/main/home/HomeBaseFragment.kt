package com.example.myapplication.ui.main.home

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.ui.SecondActivity
import com.example.myapplication.ui.main.BaseFragment

class HomeBaseFragment : BaseFragment<FragmentHomeBaseBinding>(R.layout.fragment_home_base) {
    override fun init() {
        // 초기 화면
        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_base_layout, HomeFragment(), "home")
            .commitAllowingStateLoss()

        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "recent" -> {
                    initAppbar("최근 본 상품",true)
                }
                "new" -> {
                    initAppbar("NEW",true)

                }
                "home" -> {
                    initAppbar("홈",false)
                }
            }
        }
    }
}