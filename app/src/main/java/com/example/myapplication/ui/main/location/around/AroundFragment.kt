package com.example.myapplication.ui.main.location.around

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.R.drawable.icon_favorite_whiteline
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.db.remote.model.MapModel
import com.example.myapplication.db.remote.model.StoreDetailData
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.viewmodel.MapViewModel
import kotlinx.android.synthetic.main.item_around_recycler.*
import kotlinx.android.synthetic.main.item_around_recycler.view.*


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {
    lateinit var mapViewModel: MapViewModel

    private var nearstoredata : MapModel ?= null
    private var storedetaildatalist = ArrayList<StoreDetailData>()

    override fun init() {
        // 플로팅 버튼 이벤트 처리
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        binding.aroundFab.setOnClickListener {
            parentFragmentManager
                .popBackStackImmediate(null, 0)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            Log.d("whatisthis", "000")
            val aroundAdapter =
                AroundAdapter(clicklistener = (object : AroundAdapter.ClothesClickListener {
                    override fun onItemMarketFavoriteClick(view: View, position: Int) {
//                            if(storedetaildatalist[position].market_around_favorite==false){
//                                //화면에 보여주기
//                                Glide.with(this@AroundFragment)
//                                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                                    .into(market_favorite) //보여줄 위치
//                                storedetaildatalist[position].market_around_favorite = true
//                            }else{
//                                //화면에 보여주기
//                                Glide.with(this@AroundFragment)
//                                    .load(R.drawable.icon_favorite_line) //이미지
//                                    .into(market_favorite) //보여줄 위치
//                                storedetaildatalist[position].market_around_favorite = false
//                            }
                    }

                    override fun onItemMarketLayoutClick(view: View, position: Int) {

                    }
                }))
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
            aroundAdapter.updatedata(storedetaildatalist)
            Log.d("whatisthis", "데이터 " + mapViewModel.near_store_data.value.toString())

            if(mapViewModel.near_store_data.value != null){
                nearstoredata = mapViewModel.near_store_data.value!!
                Log.d("whatisthis", "데이터 " + nearstoredata.toString())

                for (i in nearstoredata!!) {
                    mapViewModel.near_store_detail(i.id, "전체")
                    mapViewModel.near_store_detail.observe(
                        viewLifecycleOwner,
                        Observer<StoreDetailData> {
                            if (it != null) {

                                storedetaildatalist.add(it)
                                aroundAdapter.updatedata(storedetaildatalist)

                            } else {
                                Log.d("whatisthis", "1네트워크 오류가 발생했습니다.")
                            }
                        })
                }
            }else{
                nearstoredata = null
            }


//
//            // 1. 어댑터 생성 및 리사이클러뷰 연결
//            val aroundAdapter = AroundAdapter(clicklistener = (object : AroundAdapter.ClothesClickListener{
//                override fun onItemMarketFavoriteClick(view: View, position: Int) {
//                    if(marketAroundDataTemp[position].market_around_favorite==false){
//                        //화면에 보여주기
//                        Glide.with(this@AroundFragment)
//                            .load(R.drawable.icon_favorite_filledpink) //이미지
//                            .into(market_favorite) //보여줄 위치
//                        marketAroundDataTemp[position].market_around_favorite = true
//                    }else{
//                        //화면에 보여주기
//                        Glide.with(this@AroundFragment)
//                            .load(R.drawable.icon_favorite_line) //이미지
//                            .into(market_favorite) //보여줄 위치
//                        marketAroundDataTemp[position].market_around_favorite = false
//                    }
//                }
//
//                override fun onItemMarketLayoutClick(view: View, position: Int) {
//
//                }
//            }))
//
//            aroundRecyclerview.adapter = aroundAdapter
//            aroundRecyclerview.layoutManager = LinearLayoutManager(context)
//            aroundRecyclerview.addItemDecoration(OrderListDivider(0f,0f,4f,4f, Color.TRANSPARENT))
//            aroundAdapter.userList = marketAroundDataTemp
//            aroundAdapter.notifyDataSetChanged()
        }
    }
}