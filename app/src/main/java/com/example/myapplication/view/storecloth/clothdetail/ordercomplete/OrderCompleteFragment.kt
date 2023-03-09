package com.example.myapplication.view.storecloth.clothdetail.ordercomplete

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderCompleteBinding
import com.example.myapplication.base.BaseFragment

class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(R.layout.fragment_order_complete) {
    override fun init() {
        initHomeButton()
    }

    private fun initHomeButton(){
        with(binding){
            binding.goHomeButton.setOnClickListener{
                requireActivity().finish()
            }
        }
    }
}