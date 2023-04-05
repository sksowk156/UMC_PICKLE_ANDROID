package com.example.myapplication.view.main.profile.orderstatus

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusBinding
import com.example.myapplication.data.remote.model.DressOrderListDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.profile.orderstatus.detail.OrderstatusDetailFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderstatusFragment :
    BaseFragment<FragmentOrderstatusBinding>(R.layout.fragment_orderstatus) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    override fun init() {
        initAppbarName()
        hideBottomNavigation(true)
        initRecyclerView()
    }

    private fun initAppbarName() {
        if (parentFragmentManager.findFragmentByTag("completeorder") != null) { // 주문완료
            dressViewModel.get_dress_order_data("주문완료")
            initAppbar(binding.orderstatusToolbar, "주문완료", true, false)
        } else if (parentFragmentManager.findFragmentByTag("pickup") != null) { // 픽업 중
            dressViewModel.get_dress_order_data("픽업중")
            initAppbar(binding.orderstatusToolbar, "픽업 중", true, false)
        } else if (parentFragmentManager.findFragmentByTag("pickupconfirm") != null) { // 픽업 완료
            dressViewModel.get_dress_order_data("픽업완료")
            initAppbar(binding.orderstatusToolbar, "픽업완료", true, false)
        } else { // 구매 확정
            dressViewModel.get_dress_order_data("구매확정")
            initAppbar(binding.orderstatusToolbar, "구매확정", true, false)
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val orderstatusAdapter = OrderstatusAdapter(clicklistener = (object :
                OrderstatusAdapter.OrderstatusClickListener {
                override fun onItemImageClick(view: View, position: Int) {
                    // 주문한 페이지로 이동
                }

                override fun onItemDetailClick(reservationid: Int, position: Int) {
                    // 주문 상세 페이지로 이동
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.profileblank_layout,
                            OrderstatusDetailFragment()
                        )
                        .addToBackStack(null)
                        .commitAllowingStateLoss()

                    // 선택한 아이템의 주문 번호를 받아서
                    dressViewModel.get_dress_order_detail_data(reservationid)
                }

                override fun onItemInnerlayoutClick(reservationid: Int, position: Int) {
                    // 주문 상세 페이지로 이동
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.profileblank_layout,
                            OrderstatusDetailFragment()
                        )
                        .addToBackStack(null) // 현재의 fragment가(OrderstatusFragment) 저장되는 것이다.
                        .commitAllowingStateLoss()

                    dressViewModel.get_dress_order_detail_data(reservationid)
                    //주문취소
                    //reservationViewModel.set_dress_resevation_data(3)
                }
            }))

            orderstatusRecyclerview.adapter = orderstatusAdapter
            orderstatusRecyclerview.layoutManager = LinearLayoutManager(context)
            orderstatusRecyclerview.addItemDecoration(
                OrderListDivider(
                    0f,
                    0f,
                    20f,
                    20f,
                    Color.TRANSPARENT
                )
            )

            dressViewModel.dress_order_data.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {
                    }

                    is NetworkResult.Error -> {
                        Log.d("whatisthis", "OrderstatusFragment : ${it.data}, ${it.message}")
                    }

                    is NetworkResult.Success -> {
                        orderstatusAdapter.userList = it.data as ArrayList<DressOrderListDto>
                        orderstatusAdapter.notifyDataSetChanged()
                    }
                }
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dressViewModel.set_dress_order_data()
    }
}