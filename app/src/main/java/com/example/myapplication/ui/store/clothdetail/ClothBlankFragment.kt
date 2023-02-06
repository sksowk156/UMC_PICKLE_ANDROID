package com.example.myapplication.ui.store.clothdetail

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentClothBlankBinding
import com.example.myapplication.ui.base.BaseFragment


class ClothBlankFragment : BaseFragment<FragmentClothBlankBinding>(R.layout.fragment_cloth_blank) {
    override fun init() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.clothblank_layout, ClothDetailFragment(), "clothdetail")
            .commitAllowingStateLoss()
        initAppbar(binding.clothblankToolbarcontent,binding.clothblankToolbar,"",true,true)
        changeAppbar()
    }

    fun changeAppbar(){
        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "clothdetail" -> {
                    initSubAppbar("", true, true)
                }
                "pickupdetail" -> {
                    initSubAppbar("픽업 주문하기", true, false)
                }
            }
        }
    }
}