package com.example.myapplication.ui.main.favorite.item

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteItemBinding
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.*
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.ui.storecloth.clothdetail.ClothActivity
import com.example.myapplication.ui.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel


class FavoriteItemFragment :
    BaseFragment<FragmentFavoriteItemBinding>(R.layout.fragment_favorite_item), ItemClickInterface {

    lateinit var dressViewModel: DressViewModel
    lateinit var fragmentadapter: FavoriteItemAdapter

    override fun init() {
        dressViewModel = ViewModelProvider(requireParentFragment()).get(DressViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        fragmentadapter = FavoriteItemAdapter(this)

        dressViewModel.dress_like_data.observe(this, Observer<List<DressLikeDto>> { dresslikedata ->
            if(dresslikedata!=null){
                fragmentadapter.submitList(dresslikedata.toMutableList())
            }else{
                fragmentadapter.submitList(null)
                Log.d("whatisthis", "dress_like_data, 데이터 없음")
            }
        })

        binding.favoriteItemRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = fragmentadapter
        }
    }

    override fun onItemImageClick(id: Int, position: Int) {
        val intent = Intent(context, ClothActivity::class.java)
        intent.putExtra("storeName", "store1")
        intent.putExtra("clothName", "옷1")
        intent.putExtra("clothPrice", 30000)

        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        val intent = Intent(context, StoreActivity::class.java)
        startActivity(intent)
    }


    override fun onItemFavoriteClick(id: Int, position: Int) {
        // 좋아요 지우기
    }
}