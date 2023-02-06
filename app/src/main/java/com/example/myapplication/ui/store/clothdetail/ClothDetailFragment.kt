package com.example.myapplication.ui.store.clothdetail

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentClothDetailBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.inquiry.InquiryFragment
import com.example.myapplication.ui.main.profile.logout.LogoutFragment
import com.example.myapplication.ui.main.profile.myprofile.MyprofileFragment
import com.example.myapplication.ui.main.profile.notice.NoticeFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderstatusFragment
import com.example.myapplication.ui.main.profile.withdrawal.WithdrawalFragment
import com.example.myapplication.ui.store.clothdetail.pickupdetail.PickupDetailFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ClothDetailFragment : BaseFragment<FragmentClothDetailBinding>(R.layout.fragment_cloth_detail) {
    // 현재 보이는 fragment의 Tag를 저장
    private lateinit var viewpager: ViewPager2
    private lateinit var storeName: String
    private lateinit var clothName: String
    private var clothPrice: Int = 0

    override fun init() {
        //어댑터 설정
        viewpager = binding.clothdetailViewpager
        viewpager.adapter = ViewPagerAdapter(getImageList())
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //indicator 설정
        val indicator = binding.clothdetailIndicator
        indicator.setViewPager(viewpager)

        storeName = "storename"
        clothName = "clothname"
        clothPrice = 100000

        binding.clothdetailTextviewStorename.text = storeName
        binding.clothdetailTextviewClothname.text = clothName
        binding.clothdetailTextviewClothprice.text = clothPrice.toString()

        initButton()
    }

    private fun getImageList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.cardigan1,
            R.drawable.cardigan2,
            R.drawable.cardigan3,
            R.drawable.cardigan4
        )

    }

    private fun initButton(){
        binding.apply {
            // 회원탈퇴
            clothdetailTextviewOrder.setOnClickListener {
                val bottomSheet = BottomSheetFragment(requireContext())
                //bottomSheet.setContentView(R.layout.fragment_bottom_sheet)

                var bundle = Bundle()
                bundle.putString("storeName", storeName)
                bundle.putString("clothName", clothName)
                bundle.putInt("clothPrice", clothPrice)
                bottomSheet.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        }
    }

}