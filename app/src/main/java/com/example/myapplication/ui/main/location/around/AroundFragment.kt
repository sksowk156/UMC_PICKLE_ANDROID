package com.example.myapplication.ui.main.location.around

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.db.remote.model.StoreCoordDtoList
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.ui.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.StoreViewModel
import kotlinx.android.synthetic.main.item_around_recycler.*


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {
    lateinit var storeViewModel: StoreViewModel
    private var nearstoredata: StoreCoordDtoList? = null

    override fun init() {
        // 플로팅 버튼 이벤트 처리
        storeViewModel = ViewModelProvider(requireParentFragment()).get(StoreViewModel::class.java)

        binding.aroundFab.setOnClickListener {
            parentFragmentManager
                .popBackStackImmediate(null, 0)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val aroundAdapter =
                AroundAdapter(clicklistener = (object : AroundAdapter.ClothesClickListener {
                    override fun onItemMarketFavoriteClick(view: View, position: Int) {
                        if (nearstoredata?.get(position)?.store_like == false) {
                            //화면에 보여주기
                            Glide.with(this@AroundFragment)
                                .load(R.drawable.icon_favorite_filledpink) //이미지
                                .into(market_favorite) //보여줄 위치
                            nearstoredata?.get(position)?.store_like = true
                            // 좋아요 정보 갱신
                        } else {
                            //화면에 보여주기
                            Glide.with(this@AroundFragment)
                                .load(R.drawable.icon_favorite_line) //이미지
                                .into(market_favorite) //보여줄 위치
                            nearstoredata?.get(position)?.store_like = false
                            // 좋아요 정보 갱신
                        }
                    }

                    override fun onItemMarketLayoutClick(view: View, position: Int) {
                        // 버튼 클릭시 상세 페이지로 이동
                        val intent = Intent(getActivity(), StoreActivity::class.java)
                        intent.putExtra("store_id", nearstoredata?.get(position)?.store_id)
                        startActivity(intent)

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

            storeViewModel.store_near_data.observe(viewLifecycleOwner, Observer<StoreCoordDtoList> { now_StoreCoordDtoList ->
                    if(now_StoreCoordDtoList != null){
                        aroundAdapter.updatedata(now_StoreCoordDtoList)
                        nearstoredata = now_StoreCoordDtoList
                    }else{
                        Log.d("whatisthis", "store_near_data, 데이터 없음")
                    }
            })

        }
    }
}