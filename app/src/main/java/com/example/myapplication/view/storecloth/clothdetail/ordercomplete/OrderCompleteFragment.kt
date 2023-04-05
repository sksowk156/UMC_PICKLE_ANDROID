package com.example.myapplication.view.storecloth.clothdetail.ordercomplete

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderCompleteBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCompleteFragment :
    BaseFragment<FragmentOrderCompleteBinding>(R.layout.fragment_order_complete) {

    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    override fun init() {
        initHomeButton()
        dressViewModel.update_dress_reservation_data.observe(this@OrderCompleteFragment, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis", "주문결과 : ${it.data}, ${it.message}")
                }

                is NetworkResult.Success -> {
                    Log.d("whatisthis", "주문결과 : ${it.data}, ${it.message}")

                }
            }
        })
    }


    private fun initHomeButton() {
        with(binding) {
            binding.goHomeButton.setOnClickListener {
                requireActivity().finish()
            }
        }
    }
}