package com.example.myapplication.ui.store.clothdetail.pickupdetail

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPickupDetailBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData
import com.example.myapplication.ui.main.profile.orderstatus.detail.OrderstatusDetailAdapter

class PickupDetailFragment : BaseFragment<FragmentPickupDetailBinding>(R.layout.fragment_pickup_detail) {
    override fun init() {
        var storeName = arguments?.getString("storeName").toString()
        var clothName = arguments?.getString("clothName").toString()
        var clothPrice = arguments?.getInt("clothPrice")!!
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val pickupDetailAdapter = PickupDetailAdapter()

            val pickupDetailDatalist: ArrayList<OrderedClotheData> = ArrayList()
            pickupDetailDatalist.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            pickupDetailDatalist.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            pickupDetailDatalist.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))

            pickupdetailRecyclerview.adapter = pickupDetailAdapter
            pickupdetailRecyclerview.layoutManager = LinearLayoutManager(context)
            pickupDetailAdapter.userList = pickupDetailDatalist
            pickupDetailAdapter.notifyDataSetChanged()
        }
    }

}