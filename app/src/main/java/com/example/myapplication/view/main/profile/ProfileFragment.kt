package com.example.myapplication.view.main.profile

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.profile.inquiry.InquiryFragment
import com.example.myapplication.view.main.profile.logout.LogoutFragment
import com.example.myapplication.view.main.profile.myprofile.MyprofileFragment
import com.example.myapplication.view.main.profile.notice.NoticeFragment
import com.example.myapplication.view.main.profile.orderstatus.OrderstatusFragment
import com.example.myapplication.view.main.profile.withdrawal.WithdrawalFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.widget.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    val userViewModel: UserViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        binding.uservm = userViewModel

        hideBottomNavigation(false)
        initAppbar(binding.profileToolbar, "마이페이지", false, true)
        initButton()

        userViewModel.user_profile_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    userViewModel.set_home_user_name_data("피클")
                }

                is NetworkResult.Success -> {
                    userViewModel.set_home_user_name_data(it.data?.data!!.name)
                }
            }
        })

        dressViewModel.completeorder.observe(viewLifecycleOwner, Observer {
            binding.profileTextviewCompleteorder.text = it.toString()
        })
//
//        dressViewModel.pickup.observe(viewLifecycleOwner,Observer{
//            binding.profileTextviewPickup.text = it.toString()
//        })
//
//        dressViewModel.pickupconfirm.observe(viewLifecycleOwner,Observer{
//            binding.profileTextviewPickupconfirm.text = it.toString()
//        })
//
//        dressViewModel.purchaseconfirm.observe(viewLifecycleOwner,Observer{
//            binding.profileTextviewPurchaseconfirm.text = it.toString()
//        })
    }
//
    private fun initButton(){
        binding.apply {
            // 마이페이지
            profileTextviewMyprofile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, MyprofileFragment(),"myprofile")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            // 주문현황
            profileInnerlayoutCompleteorder.setOnClickListener {
                dressViewModel.get_dress_order_data("주문완료")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"completeorder")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileInnerlayoutPickup.setOnClickListener {
                dressViewModel.get_dress_order_data("픽업중")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"pickup")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileInnerlayoutPickupconfirm.setOnClickListener {
                dressViewModel.get_dress_order_data("픽업완료")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileblank_layout, OrderstatusFragment(),"pickupconfirm")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            profileInnerlayoutPurchaseconfirm.setOnClickListener {
                dressViewModel.get_dress_order_data("구매확정")
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