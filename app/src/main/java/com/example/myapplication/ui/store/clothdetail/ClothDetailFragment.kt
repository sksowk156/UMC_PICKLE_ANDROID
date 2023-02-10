package com.example.myapplication.ui.store.clothdetail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentClothDetailBinding
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.store.clothdetail.order.OrderBottomSheetFragment
import com.example.myapplication.viewmodel.DressViewModel


class ClothDetailFragment : BaseFragment<FragmentClothDetailBinding>(R.layout.fragment_cloth_detail) {
    lateinit var dressViewModel: DressViewModel
    lateinit var dressdetailAdapter : ClothDetailAdapter

    // 현재 보이는 fragment의 Tag를 저장
    private lateinit var dressimage_viewpager: ViewPager2
    private lateinit var storeName: String
    private lateinit var clothName: String
    private var clothPrice: Int = 0

    override fun init() {
        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)
        dressdetailAdapter = ClothDetailAdapter()

        dressViewModel.dress_detail_data.observe(viewLifecycleOwner, Observer<DressDetailDto> { now_dressdetail ->
            if (now_dressdetail != null) {
                binding.clothdetailTextviewStorename.text = now_dressdetail.store_name
                binding.clothdetailTextviewClothname.text = now_dressdetail.dress_name
                binding.clothdetailTextviewClothprice.text = now_dressdetail.dress_price
                dressdetailAdapter.submitList(now_dressdetail.dress_image_url_list?.toMutableList())
            } else {
                Log.d("whatisthis", "11네트워크 오류가 발생했습니다.")
            }
        })
        //어댑터 설정
        dressimage_viewpager = binding.clothdetailViewpager
        dressimage_viewpager.adapter = dressdetailAdapter
        dressimage_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //indicator 설정
        val indicator = binding.clothdetailIndicator
        indicator.setViewPager(dressimage_viewpager)

        initButton()
    }

    private fun initButton(){
        binding.apply {
            clothdetailTextviewOrder.setOnClickListener {
                val bottomSheet = OrderBottomSheetFragment()
                //bottomSheet.setContentView(R.layout.fragment_bottom_sheet)

//                var bundle = Bundle()
//                bundle.putString("storeName", storeName)
//                bundle.putString("clothName", clothName)
//                bundle.putInt("clothPrice", clothPrice)
//                bottomSheet.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)

            }

        }
    }

}