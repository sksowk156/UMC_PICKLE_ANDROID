package com.example.myapplication.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.ui.main.BaseFragment

class HomeBaseFragment : BaseFragment<FragmentHomeBaseBinding>(R.layout.fragment_home_base) {


    override fun init() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_base_layout, HomeFragment(),"home")
            .commitAllowingStateLoss()
        // Appbar
        initAppbar_profile("홈")
        childFragmentManager.addOnBackStackChangedListener {
            when(childFragmentManager.fragments.last().tag){
                "recent"->{
                    initAppbar_btn("최근 본 상품")
                }
                "new"->{
                    initAppbar_btn("NEW")
                }

            }
        }


    }

    private fun initAppbar_profile(name:String){
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            toolbar = homeBaseToolbar.toolbar
            // 툴바 이름정하기
            toolbar.setTitle(name)
            // 툴바 뒤로가기 버튼 지우기
            toolbar.setNavigationIcon(null)
            // 툴바 메뉴 넣기
            toolbar.inflateMenu(R.menu.menu_appbar)
            // 툴바 클릭 이벤트
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search -> {
                    }
                    R.id.notification -> {
                    }
                }
                true
            }
        }
    }

    private fun initAppbar_btn(name:String){
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            toolbar = homeBaseToolbar.toolbar
            // 툴바 이름정하기
            toolbar.setTitle(name)
            // 툴바 뒤로가기 버튼 아이콘 정하기
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
            // 툴바 뒤로가기 버튼 클릭 이벤트
            toolbar.setNavigationOnClickListener {
                childFragmentManager.popBackStackImmediate(null,0)
            }
            // 툴바 메뉴 지우기
            toolbar.menu.clear()
        }
    }


}