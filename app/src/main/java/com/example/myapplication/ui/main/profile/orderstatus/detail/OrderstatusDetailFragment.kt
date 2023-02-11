package com.example.myapplication.ui.main.profile.orderstatus.detail

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusDetailBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData


class OrderstatusDetailFragment : BaseFragment<FragmentOrderstatusDetailBinding>(R.layout.fragment_orderstatus_detail) {
    override fun init() {
        initAppbar(binding.orderstatusToolbar, "주문 상세보기", true, false)
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusDetailAdapter()

            val OrderedClotheDataList: ArrayList<OrderedClotheData> = ArrayList()
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))

            detailRecyclerview.adapter = orderstatusAdapter
            detailRecyclerview.layoutManager = LinearLayoutManager(context)
            orderstatusAdapter.userList = OrderedClotheDataList
            orderstatusAdapter.notifyDataSetChanged()
        }
    }
}