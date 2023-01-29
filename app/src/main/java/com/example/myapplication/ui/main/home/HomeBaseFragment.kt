package com.example.myapplication.ui.main.home

import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.ui.base.AppbarData
import com.example.myapplication.ui.base.BaseFragment

class HomeBaseFragment : BaseFragment<FragmentHomeBaseBinding>(R.layout.fragment_home_base) {

    lateinit var appbarData: AppbarData

    override fun init() {
        // 초기 화면
        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_base_layout, HomeFragment(), "home")
            .commitAllowingStateLoss()
        changeAppbar()
    }

    fun changeAppbar(){
        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "recent" -> {
                    appbarData = initAppbar("최근 본 상품",true)
                }
                "new" -> {
                    appbarData = initAppbar("NEW",true)
                }
                "home" -> {
                    appbarData = initAppbar("홈",false)
                }
            }
        }
    }
}