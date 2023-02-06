package com.example.myapplication.ui.main.favorite.store


import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteStoreBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.location.MapAroundData
import com.example.myapplication.ui.main.location.around.AroundAdapter
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.ui.store.storedetail.StoreActivity
import kotlinx.android.synthetic.main.fragment_favorite_store.*
import kotlinx.android.synthetic.main.item_around_recycler.*


class FavoriteStoreFragment :
    BaseFragment<FragmentFavoriteStoreBinding>(R.layout.fragment_favorite_store) {

    lateinit var favoritestoredapter: AroundAdapter
    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val marketAroundDataTemp: ArrayList<MapAroundData> = ArrayList()
            marketAroundDataTemp.add(MapAroundData("1", "title1", "subtitle1", true))
            marketAroundDataTemp.add(MapAroundData("2", "title2", "subtitle2", true))
            marketAroundDataTemp.add(MapAroundData("3", "title3", "subtitle3", true))
            marketAroundDataTemp.add(MapAroundData("4", "title4", "subtitle4", true))
            marketAroundDataTemp.add(MapAroundData("5", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("6", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("7", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("8", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("9", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("10", "title5", "subtitle5", true))
            marketAroundDataTemp.add(MapAroundData("11", "title5", "subtitle5", true))

            // 1. 어댑터 생성 및 리사이클러뷰 연결
            favoritestoredapter =
                AroundAdapter(clicklistener = (object : AroundAdapter.ClothesClickListener {
                    override fun onItemMarketFavoriteClick(view: View, position: Int) {
                        if (marketAroundDataTemp[position].market_around_favorite == false) {
                            //화면에 보여주기
                            Glide.with(this@FavoriteStoreFragment)
                                .load(R.drawable.icon_favorite_filledpink) //이미지
                                .into(market_favorite) //보여줄 위치
                            marketAroundDataTemp[position].market_around_favorite = true
                        } else {
                            //화면에 보여주기
                            Glide.with(this@FavoriteStoreFragment)
                                .load(R.drawable.icon_favorite_line) //이미지
                                .into(market_favorite) //보여줄 위치
                            marketAroundDataTemp[position].market_around_favorite = false
                        }
                    }

                    override fun onItemMarketLayoutClick(view: View, position: Int) {
                        val intent = Intent(context, StoreActivity::class.java)
                        startActivity(intent)
                    }
                }))

            favorite_store_recyclerview.adapter = favoritestoredapter
            favorite_store_recyclerview.layoutManager = LinearLayoutManager(context)
            favorite_store_recyclerview.addItemDecoration(OrderListDivider(0f,0f,4f,4f, Color.TRANSPARENT))
            favoritestoredapter.userList = marketAroundDataTemp
            favoritestoredapter.notifyDataSetChanged()
        }
    }

}