package com.example.myapplication.view.main.location.around

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.data.remote.model.UpdateStoreLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.example.myapplication.widget.utils.EventObserver
import com.example.myapplication.widget.utils.ItemListClickInterface
import com.example.myapplication.widget.utils.NetworkResult


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {
    lateinit var storeViewModel: StoreViewModel
    lateinit var homeViewModel: HomeViewModel

    override fun init() {
        // 플로팅 버튼 이벤트 처리
        storeViewModel = (activity as SecondActivity).storeViewModel
        homeViewModel = (activity as SecondActivity).homeViewModel

        binding.storevm = storeViewModel
//        binding.lifecycleOwner = this@AroundFragment

        // 플로팅 버튼 클릭시 이벤트 처리
        storeViewModel.apply {
            floatingaround_bt_event.observe(this@AroundFragment, EventObserver {
                parentFragmentManager
                    .popBackStackImmediate(null, 0)
            })
        }
//        binding.aroundFab.setOnClickListener {
//            parentFragmentManager
//                .popBackStackImmediate(null, 0)
//        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val aroundAdapter =
                AroundAdapter(clicklistener = (object : ItemListClickInterface {
                    override fun onItemMarketFavoriteClick(
                        like: Boolean,
                        id: Int,
                        view: View,
                        position: Int
                    ) {
                        storeViewModel.set_store_like_data(UpdateStoreLikeDto(false, id))
                        homeViewModel.get_home_data(
                            homeViewModel.home_latlng.value!!.first,
                            homeViewModel.home_latlng.value!!.second
                        )
                        storeViewModel.get_store_like_data()
                    }

                    override fun onItemMarketLayoutClick(id: Int, position: Int) {
                        val intent = Intent(context, StoreActivity::class.java)
                        intent.putExtra("store_id", id)
                        startActivity(intent)
                    }
                }))

            storeViewModel.store_near_data.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {
                    }

                    is NetworkResult.Error -> {
                        Log.d("whatisthis", "store_near_data, 데이터 없음")
                    }

                    is NetworkResult.Success -> {
                        aroundAdapter.submitList(it.data!!.toMutableList())
                    }
                }
            })

            storeViewModel.update_store_like_data.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {
                    }

                    is NetworkResult.Error -> {
                        Log.d("whatisthis", "update_store_like_data, 데이터 없음")
                    }

                    is NetworkResult.Success -> {
                        storeViewModel.get_store_near_data(
                            storeViewModel.screen_latlng.value!!.first,
                            storeViewModel.screen_latlng.value!!.second
                        )
                    }
                }
            })

            aroundRecyclerview.adapter = aroundAdapter
            aroundRecyclerview.layoutManager = LinearLayoutManager(context)
            aroundRecyclerview.addItemDecoration(
                OrderListDivider(
                    0f,
                    0f,
                    4f,
                    4f,
                    Color.TRANSPARENT
                )
            )

        }
    }
}