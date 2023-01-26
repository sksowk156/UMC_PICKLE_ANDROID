package com.example.myapplication.ui.main.profile

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.profile.inquiry.InquiryFragment
import com.example.myapplication.ui.main.profile.logout.LogoutFragment
import com.example.myapplication.ui.main.profile.myprofile.MyprofileFragment
import com.example.myapplication.ui.main.profile.notice.NoticeFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderstatusFragment
import com.example.myapplication.ui.main.profile.withdrawal.WithdrawalFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override fun init() {
        hideBottomNavigation(false)
        initButton()
    }

    private fun initButton(){
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            // 마이페이지
            profileTextviewMyprofile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, MyprofileFragment(),"myprofile")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            // 주문현황
            profileTextviewCompleteorder.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"completeorder")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileTextviewPickup.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"pickup")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileTextviewPickupconfirm.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"pickupconfirm")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileTextviewPurchaseconfirm.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"purchaseconfirm")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            // 공지사항
            profileTextviewAnnouncement.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, NoticeFragment(),"notice")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            // 문의사항
            profileTextviewQuestions.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, InquiryFragment(),"inquiry")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            // 로그아웃
            profileTextviewLogout.setOnClickListener {
                val bottomSheetDialogFragment : BottomSheetDialogFragment = LogoutFragment()
                bottomSheetDialogFragment.show(parentFragmentManager,null)
            }

            // 회원탈퇴
            profileTextviewWithdrawal.setOnClickListener {
                val bottomSheetDialogFragment : BottomSheetDialogFragment = WithdrawalFragment()
                bottomSheetDialogFragment.show(parentFragmentManager,null)
            }
        }
    }
}