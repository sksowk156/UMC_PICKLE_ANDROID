package com.example.myapplication.ui.main.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBlankBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.profile.inquiry.InquiryFragment
import com.example.myapplication.ui.main.profile.myprofile.MyprofileFragment
import com.example.myapplication.ui.main.profile.notice.NoticeFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderstatusFragment

class ProfileBlankFragment : BaseFragment<FragmentProfileBlankBinding>(R.layout.fragment_profile_blank) {

    override fun init() {
        childFragmentManager.beginTransaction()
            .replace(R.id.profileblank_layout, ProfileFragment(),"profile")
            .commitAllowingStateLoss()

        // Appbar
        initAppbar_profile("마이페이지")
        childFragmentManager.addOnBackStackChangedListener {
            when(childFragmentManager.fragments.last().tag){
                "myprofile"->{
                    initAppbar_btn("내 정보 수정")
                }
                "completeorder"->{
                    initAppbar_btn("주문 완료")
                }
                "pickup"->{
                    initAppbar_btn("픽업 중")
                }
                "pickupconfirm"->{
                    initAppbar_btn("픽업 완료")
                }
                "purchaseconfirm"->{
                    initAppbar_btn("구매 확정")
                }
                "notice"->{
                    initAppbar_btn("공지사항")
                }
                "inquiry"->{
                    initAppbar_btn("문의사항")
                }
                "profile"->{
                    initAppbar_profile("마이페이지")
                }
            }
        }


    }

    private fun initAppbar_profile(name:String){
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            toolbar = profileblankToolbar.toolbar
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
            toolbar = profileblankToolbar.toolbar
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