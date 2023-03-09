package com.example.myapplication.view.main.profile.orderstatus.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusDetailBinding
import com.example.myapplication.data.remote.model.ReservedDressDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.widget.utils.NetworkResult


class OrderstatusDetailFragment : BaseFragment<FragmentOrderstatusDetailBinding>(R.layout.fragment_orderstatus_detail) {
    lateinit var dressViewModel: DressViewModel

    override fun init() {

        dressViewModel = (activity as SecondActivity).dressViewModel

        initAppbar(binding.orderstatusToolbar, "주문 상세보기", true, false)
        hideBottomNavigation(true)
        initRecyclerView()

        binding.cancelButton.setOnClickListener(){
            reviseRecyclerView()
        }

    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusDetailAdapter()
            detailRecyclerview.adapter = orderstatusAdapter
            detailRecyclerview.layoutManager = LinearLayoutManager(context)
            dressViewModel.dress_order_detail_data.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {
                    }

                    is NetworkResult.Error -> {
                        Log.d("whatisthis","OrderstatusDetailFragment : 데이터없음")
                    }

                    is NetworkResult.Success -> {
                        orderstatusAdapter.userList = it.data!!.get(0).reservedDressList as ArrayList<ReservedDressDto>
                        orderstatusAdapter.notifyDataSetChanged()
                        detailTextviewOrdernumber.text = it.data!!.get(0).dress_reservation_id.toString()
                        detailTextviewAddress.text = it.data!!.get(0).store_address
                        detailTextviewOperationhours.text = it.data!!.get(0).hours_of_operation.toString()
                        detailTextviewPickupdatetime.text = it.data!!.get(0).pickup_datetime
                        detailTextviewRequests.text = it.data!!.get(0).comment
                        detailTextviewTotalprice.text = it.data!!.get(0).price
                        detailTextviewStorename.text = it.data!!.get(0).store_name
                    }
                }
            })

        }
    }


    private fun reviseRecyclerView(){

    }
}