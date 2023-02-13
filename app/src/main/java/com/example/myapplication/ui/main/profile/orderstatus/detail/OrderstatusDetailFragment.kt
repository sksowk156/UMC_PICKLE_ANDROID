package com.example.myapplication.ui.main.profile.orderstatus.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusDetailBinding
import com.example.myapplication.db.remote.DressOrderListDto
import com.example.myapplication.db.remote.model.DressOrderDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData
import com.example.myapplication.viewmodel.DressViewModel


class OrderstatusDetailFragment : BaseFragment<FragmentOrderstatusDetailBinding>(R.layout.fragment_orderstatus_detail) {
    lateinit var dressViewModel: DressViewModel

    override fun init() {

        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)

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
            dressViewModel.dress_reservation_dress_data.observe(viewLifecycleOwner, Observer {
                orderstatusAdapter.userList = it as ArrayList<DressOrderDto>
                orderstatusAdapter.notifyDataSetChanged()
                detailTextviewOrdernumber.text = it.last().reserved_dress_id.toString()
                detailTextviewAddress.text = it.last().store_address
                detailTextviewOperationhours.text = it.last().hours_of_operation.toString()
                detailTextviewPickupdatetime.text = it.last().pickup_datetime
                detailTextviewRequests.text = it.last().comment
                detailTextviewTotalprice.text = it.last().price
                detailTextviewStorename.text = it.last().store_name
            })

        }
    }


    private fun reviseRecyclerView(){

    }
}