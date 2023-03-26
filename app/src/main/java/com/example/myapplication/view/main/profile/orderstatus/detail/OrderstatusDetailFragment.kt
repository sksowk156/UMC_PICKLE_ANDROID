package com.example.myapplication.view.main.profile.orderstatus.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusDetailBinding
import com.example.myapplication.data.remote.model.ReservedDressDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.remote.model.DressOrderDto
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderstatusDetailFragment : BaseFragment<FragmentOrderstatusDetailBinding>(R.layout.fragment_orderstatus_detail) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    override fun init() {
        initAppbar(binding.orderstatusToolbar, "주문 상세보기", true, false)
        hideBottomNavigation(true)
        initRecyclerView()

        binding.cancelButton.setOnClickListener(){
            reviseRecyclerView()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusDetailAdapter()
            detailRecyclerview.adapter = orderstatusAdapter
            detailRecyclerview.layoutManager = LinearLayoutManager(context)
            dressViewModel.dress_order_detail_data.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {
                        detailTextviewOrdernumber.text = ""
                        detailTextviewAddress.text = "매장 위치"
                        detailTextviewOperationhours.text = "운영시간"
                        detailTextviewPickupdatetime.text = "0000.00.00"
                        detailTextviewRequests.text = "없음"
                        detailTextviewTotalprice.text = "원"
                        detailTextviewStorename.text = "매장명"
                    }

                    is NetworkResult.Error -> {
                        Log.d("whatisthis","OrderstatusDetailFragment : 데이터없음")

                    }
                    is NetworkResult.Success -> {
                        detailTextviewOrdernumber.text = it.data!![0].dress_reservation_id.toString()
                        detailTextviewAddress.text = it.data[0].store_address
                        detailTextviewOperationhours.text = it.data[0].hours_of_operation.toString()
                        detailTextviewPickupdatetime.text = it.data[0].pickup_datetime
                        if(it.data[0].comment.isEmpty()){
                            detailTextviewRequests.text = "없음"
                        }else{
                            detailTextviewRequests.text = it.data[0].comment
                        }
                        detailTextviewTotalprice.text = it.data[0].price
                        detailTextviewStorename.text = it.data[0].store_name
                        orderstatusAdapter.userList = it.data[0].reservedDressList as ArrayList<ReservedDressDto>
                        orderstatusAdapter.notifyDataSetChanged()
                    }
                }
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dressViewModel.set_dress_order_detail_data()

    }

    private fun reviseRecyclerView(){

    }
}