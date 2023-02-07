package com.example.myapplication.ui.main.profile

import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBlankBinding
import com.example.myapplication.ui.base.BaseFragment

class ProfileBlankFragment :
    BaseFragment<FragmentProfileBlankBinding>(R.layout.fragment_profile_blank) {

    override fun savedatainit() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.profileblank_layout, ProfileFragment(), "profile")
            .commitAllowingStateLoss()    }

    override fun init() {
        initAppbar(binding.profileblankToolbarcontent,binding.profileblankToolbar,"마이페이지",false,true)
        changeAppbar()
    }

    fun changeAppbar(){
        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "myprofile" -> {
                    initSubAppbar("내 정보 수정", true, false)
                }
                "completeorder" -> {
                    initSubAppbar("주문 완료", true, false)
                }
                "pickup" -> {
                    initSubAppbar("픽업 중", true, false)
                }
                "pickupconfirm" -> {
                    initSubAppbar("픽업 완료", true, false)
                }
                "purchaseconfirm" -> {
                    initSubAppbar("구매 확정", true, false)
                }
                "notice" -> {
                    initSubAppbar("공지사항", true, false)
                }
                "inquiry" -> {
                    initSubAppbar("문의사항", true, false)
                }
                "profile" -> {
                    initSubAppbar("마이페이지", false, true)
                }
                "orderstatusdetail" -> {
                    initSubAppbar("주문 상세보기", true, false)
                }
                "noticedetail"->{
                    initSubAppbar("공지사항",true, false)
                }
            }
        }
    }
}