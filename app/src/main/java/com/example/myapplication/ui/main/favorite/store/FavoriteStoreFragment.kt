package com.example.myapplication.ui.main.favorite.store


import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteStoreBinding
import com.example.myapplication.db.remote.model.StoreLikeDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.ui.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.StoreViewModel
import kotlinx.android.synthetic.main.fragment_favorite_store.*
import kotlinx.android.synthetic.main.item_around_recycler.*


class FavoriteStoreFragment :
    BaseFragment<FragmentFavoriteStoreBinding>(R.layout.fragment_favorite_store) {

    lateinit var storeViewModel: StoreViewModel
    lateinit var favoritestoredapter: FavoriteStoreAdapter
    private var storelikedata: StoreLikeDto? = null


    override fun init() {
        storeViewModel = ViewModelProvider(requireParentFragment()).get(StoreViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
//             1. 어댑터 생성 및 리사이클러뷰 연결
            favoritestoredapter =
                FavoriteStoreAdapter(clicklistener = (object :
                    FavoriteStoreAdapter.ClothesClickListener {
                    override fun onItemMarketFavoriteClick(view: View, position: Int) {
                        // 좋아요 지우기
                    }

                    override fun onItemMarketLayoutClick(view: View, position: Int) {
                        val intent = Intent(context, StoreActivity::class.java)
                        startActivity(intent)
                    }
                }))

            storeViewModel.store_like_data.observe(
                viewLifecycleOwner,
                Observer<List<StoreLikeDto>> { now_storelikedata ->
                    if(now_storelikedata!=null){
                        favoritestoredapter.submitList(now_storelikedata.toMutableList())
                    }else{
                        favoritestoredapter.submitList(null)
                        Log.d("whatisthis", "_store_like_data, 데이터 없음")
                    }
                })

            favorite_store_recyclerview.adapter = favoritestoredapter
            favorite_store_recyclerview.layoutManager = LinearLayoutManager(context)
            favorite_store_recyclerview.addItemDecoration(
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