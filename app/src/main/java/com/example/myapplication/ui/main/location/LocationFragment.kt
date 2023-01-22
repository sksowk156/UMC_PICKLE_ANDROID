package com.example.myapplication.ui.main.location

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentLocationBinding
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.home.HomeViewModel


class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {
    override fun init() {
        initAppBar()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.location_layout, MapFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    // Appbar 초기화
    private fun initAppBar() {
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            toolbar = locationToolbar.toolbar
            // 툴바 이름정하기
            toolbar.setTitle("주변매장")
//            // 툴바 뒤로가기 버튼 아이콘 정하기
//            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
//            // 툴바 뒤로가기 버튼 클릭 이벤트
//            toolbar.setNavigationOnClickListener {  }
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
}