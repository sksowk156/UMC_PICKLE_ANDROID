package com.example.myapplication.ui.main.location.around

import android.graphics.Color
import android.view.View
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.R.drawable.icon_favorite_whiteline
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.location.MapAroundData
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import kotlinx.android.synthetic.main.item_around_recycler.*
import kotlinx.android.synthetic.main.item_around_recycler.view.*


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {

    override fun init() {
        // 플로팅 버튼 이벤트 처리
        binding.aroundFab.setOnClickListener {
            parentFragmentManager
                .popBackStackImmediate(null, 0)
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding) {
            val marketAroundDataTemp: ArrayList<MapAroundData> = ArrayList()
            marketAroundDataTemp.add(MapAroundData("1", "title1", "subtitle1",false))
            marketAroundDataTemp.add(MapAroundData("2", "title2", "subtitle2",true))
            marketAroundDataTemp.add(MapAroundData("3", "title3", "subtitle3",false))
            marketAroundDataTemp.add(MapAroundData("4", "title4", "subtitle4",true))
            marketAroundDataTemp.add(MapAroundData("5", "title5", "subtitle5",true))

            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val aroundAdapter = AroundAdapter(clicklistener = (object : AroundAdapter.ClothesClickListener{
                override fun onItemMarketFavoriteClick(view: View, position: Int) {
                    if(marketAroundDataTemp[position].market_around_favorite==false){
                        //화면에 보여주기
                        Glide.with(this@AroundFragment)
                            .load(R.drawable.icon_favorite_filledpink) //이미지
                            .into(market_favorite) //보여줄 위치
                        marketAroundDataTemp[position].market_around_favorite = true
                    }else{
                        //화면에 보여주기
                        Glide.with(this@AroundFragment)
                            .load(R.drawable.icon_favorite_line) //이미지
                            .into(market_favorite) //보여줄 위치
                        marketAroundDataTemp[position].market_around_favorite = false
                    }
                }

                override fun onItemMarketLayoutClick(view: View, position: Int) {

                }
            }))

            aroundRecyclerview.adapter = aroundAdapter
            aroundRecyclerview.layoutManager = LinearLayoutManager(context)
            aroundRecyclerview.addItemDecoration(OrderListDivider(0f,0f,4f,4f, Color.TRANSPARENT))
            aroundAdapter.userList = marketAroundDataTemp
            aroundAdapter.notifyDataSetChanged()
        }
    }
}