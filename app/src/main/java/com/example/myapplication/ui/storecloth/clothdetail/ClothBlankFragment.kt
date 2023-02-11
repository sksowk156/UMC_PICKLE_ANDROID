package com.example.myapplication.ui.storecloth.clothdetail

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentClothBlankBinding
import com.example.myapplication.ui.base.BaseFragment


class ClothBlankFragment : BaseFragment<FragmentClothBlankBinding>(R.layout.fragment_cloth_blank) {

    override fun savedatainit() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.clothblank_layout, ClothDetailFragment(), "clothdetail")
            .commitAllowingStateLoss()
    }

    override fun init() {}
}