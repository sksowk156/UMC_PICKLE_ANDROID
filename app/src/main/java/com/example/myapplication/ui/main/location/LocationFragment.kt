package com.example.myapplication.ui.main.location

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.location.map.MapFragment


class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {
    override fun init() {
        initAppBar()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.location_layout, MapFragment())
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