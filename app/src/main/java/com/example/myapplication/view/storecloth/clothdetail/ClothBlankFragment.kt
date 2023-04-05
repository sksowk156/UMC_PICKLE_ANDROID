package com.example.myapplication.view.storecloth.clothdetail

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentClothBlankBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.storecloth.clothdetail.clothdetail.ClothDetailFragment


class ClothBlankFragment : BaseFragment<FragmentClothBlankBinding>(R.layout.fragment_cloth_blank) {

    override fun savedatainit() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.clothblank_layout, ClothDetailFragment(), "clothdetail")
            .commitAllowingStateLoss()
    }

    override fun init() {
    }

}