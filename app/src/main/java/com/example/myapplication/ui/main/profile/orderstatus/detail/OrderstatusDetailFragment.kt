package com.example.myapplication.ui.main.profile.orderstatus.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusDetailBinding
import com.example.myapplication.db.remote.model.DressOrderDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData
import com.example.myapplication.viewmodel.DressViewModel


class OrderstatusDetailFragment : BaseFragment<FragmentOrderstatusDetailBinding>(R.layout.fragment_orderstatus_detail) {
    lateinit var dressViewModel: DressViewModel




    override fun init() {

        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)
        dressViewModel.dress_reservation_dress_data.observe(viewLifecycleOwner, Observer {
            binding.detailTextviewOrdernumber.text = it.reserved_dress_id.toString()

        })

        initAppbar(binding.orderstatusToolbar, "주문 상세보기", true, false)
        hideBottomNavigation(true)
        initRecyclerView()

        binding.cancelButton.setOnClickListener(){
            reviseRecyclerView()
        }

    }

    val OrderedClotheDataList: ArrayList<OrderedClotheData> = ArrayList()

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusDetailAdapter()


            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
           OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
           OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))

            detailRecyclerview.adapter = orderstatusAdapter
            detailRecyclerview.layoutManager = LinearLayoutManager(context)
            orderstatusAdapter.userList = OrderedClotheDataList
            orderstatusAdapter.notifyDataSetChanged()
        }
    }


    private fun reviseRecyclerView(){

    }
}