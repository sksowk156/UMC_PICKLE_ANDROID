package com.example.myapplication.ui.main.profile.orderstatus

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.detail.OrderstatusDetailFragment

class OrderstatusFragment : BaseFragment<FragmentOrderstatusBinding>(R.layout.fragment_orderstatus) {
    override fun init() {
        initAppbarName()
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initAppbarName(){
        if(parentFragmentManager.findFragmentByTag("completeorder")!=null){ // 주문완료
            initAppbar(binding.orderstatusToolbar, "주문완료", true, false)

        }else if(parentFragmentManager.findFragmentByTag("pickup")!=null){ // 픽업 중
            initAppbar(binding.orderstatusToolbar, "픽업 중", true, false)

        }else if(parentFragmentManager.findFragmentByTag("pickupconfirm")!=null){ // 픽업 완료
            initAppbar(binding.orderstatusToolbar, "픽업완료", true, false)

        }else{ // 구매 확정
            initAppbar(binding.orderstatusToolbar, "구매확정", true, false)
        }
    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusAdapter(clicklistener = (object : OrderstatusAdapter.OrderstatusClickListener{
                override fun onItemClothImageClick(view: View, position: Int) {
                    // 주문한 페이지로 이동
                }

                override fun onItemDetailClick(view: View, position: Int) {
                    // 주문 상세 페이지로 이동
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.profileblank_layout, OrderstatusDetailFragment() ,"orderstatusdetail")
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                override fun onItemInnerlayoutClick(view: View, position: Int) {
                    // 주문 상세 페이지로 이동
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.profileblank_layout, OrderstatusDetailFragment() ,"orderstatusdetail")
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }))

            val OrderedClotheDataList: ArrayList<OrderedClotheData> = ArrayList()
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))
            OrderedClotheDataList.add(OrderedClotheData("20200204",R.drawable.cardigan1,"ㄴㅁㅇ","옷","8700","검정","M"))

            orderstatusRecyclerview.adapter = orderstatusAdapter
            orderstatusRecyclerview.layoutManager = LinearLayoutManager(context)
            orderstatusRecyclerview.addItemDecoration(OrderListDivider(0f,0f,20f,20f, Color.TRANSPARENT))
            orderstatusAdapter.userList = OrderedClotheDataList
            orderstatusAdapter.notifyDataSetChanged()
        }
    }
}