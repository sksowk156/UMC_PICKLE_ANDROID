package com.example.myapplication.ui.main.profile.orderstatus

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusBinding
import com.example.myapplication.ui.base.BaseFragment

class OrderstatusFragment : BaseFragment<FragmentOrderstatusBinding>(R.layout.fragment_orderstatus) {
    override fun init() {
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusAdapter()

            val ClotheDataList: ArrayList<ClotheData> = ArrayList()
            ClotheDataList.add(ClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700"))
            ClotheDataList.add(ClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700"))
            ClotheDataList.add(ClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700"))
            ClotheDataList.add(ClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700"))
            ClotheDataList.add(ClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700"))

            orderstatusRecyclerview.adapter = orderstatusAdapter
            orderstatusRecyclerview.layoutManager = LinearLayoutManager(context)
            orderstatusRecyclerview.addItemDecoration(OrderListDivider(0f,0f,20f,20f, Color.TRANSPARENT))
            orderstatusAdapter.userList = ClotheDataList
            orderstatusAdapter.notifyDataSetChanged()
        }
    }
}