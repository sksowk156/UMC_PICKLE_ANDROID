package com.example.myapplication.ui.main.home.newclothe

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewBinding
import com.example.myapplication.db.remote.model.DressHomeDto
import com.example.myapplication.db.remote.model.UpdateDressLikeDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.ItemCardClickInterface
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.ui.storecloth.clothdetail.ClothActivity
import com.example.myapplication.ui.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel


class NewFragment : BaseFragment<FragmentNewBinding>(R.layout.fragment_new),
    ItemCardClickInterface {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dressViewModel: DressViewModel

    private lateinit var fragmentadapter: HomeRecommendAdapter

    override fun init() {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        dressViewModel = ViewModelProvider(this).get(DressViewModel::class.java)

        initAppbar(binding.newToolbar, "NEW", true, false)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        fragmentadapter = HomeRecommendAdapter(this@NewFragment)

        homeViewModel.home_data.observe(viewLifecycleOwner, Observer<DressHomeDto> { now_homeModel ->
                if (now_homeModel != null) {
//                    fragmentadapter.submitList(now_homeModel.newDresses?.toMutableList())
                } else {
                    Log.d("whatisthis", "home_data, 없음")
                }
            })

        binding.newRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = fragmentadapter
        }
    }

    override fun onItemClothImageClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), StoreActivity::class.java)
        intent.putExtra("store_id", id)
        startActivity(intent)
    }

    override fun onItemClothFavoriteClick(like:Boolean, id: Int, view : View, position: Int) {
        if (like) {
            Glide.with(this)
                .load(R.drawable.icon_favorite_whiteline) //이미지
                .into(view as ImageView) //보여줄 위치
            // 좋아요 정보 갱신 요청
            dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
            dressViewModel.get_dress_like_data()
        }
        else {
            Glide.with(this)
                .load(R.drawable.icon_favorite_filledpink) //이미지
                .into(view as ImageView)  //보여줄 위치
            // 좋아요 정보 갱신 요청
            dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
            dressViewModel.get_dress_like_data()
        }
    }
}