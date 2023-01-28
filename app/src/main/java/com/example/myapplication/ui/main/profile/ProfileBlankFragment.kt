package com.example.myapplication.ui.main.profile

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBlankBinding
import com.example.myapplication.ui.SecondActivity
import com.example.myapplication.ui.main.BaseFragment

class ProfileBlankFragment :
    BaseFragment<FragmentProfileBlankBinding>(R.layout.fragment_profile_blank) {

    override fun init() {
        childFragmentManager.beginTransaction()
            .replace(R.id.profileblank_layout, ProfileFragment(), "profile")
            .commitAllowingStateLoss()

        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "myprofile" -> {
                    initAppbar("내 정보 수정",true)
                }
                "completeorder" -> {
                    initAppbar("주문 완료",true)
                }
                "pickup" -> {
                    initAppbar("픽업 중",true)
                }
                "pickupconfirm" -> {
                    initAppbar("픽업 완료",true)
                }
                "purchaseconfirm" -> {
                    initAppbar("구매 확정",true)
                }
                "notice" -> {
                    initAppbar("공지사항",true)
                }
                "inquiry" -> {
                    initAppbar("문의사항",true)
                }
                "profile" -> {
                    initAppbar("마이페이지",false)
                }
            }
        }
    }
}