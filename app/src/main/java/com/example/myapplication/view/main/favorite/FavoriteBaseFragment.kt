package com.example.myapplication.view.main.favorite

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBaseBinding
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.data.remote.model.UpdateStoreLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.main.favorite.item.FavoriteClothFragment
import com.example.myapplication.view.main.favorite.store.FavoriteStoreFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.example.myapplication.widget.utils.NetworkResult
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBaseFragment :
    BaseFragment<FragmentFavoriteBaseBinding>(R.layout.fragment_favorite_base) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    val storeViewModel: StoreViewModel by activityViewModels<StoreViewModel>()

    override fun init() {
        dressViewModel.get_dress_like_data()
        storeViewModel.get_store_like_data()

        dressViewModel.update_dress_like_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis","FavoriteBaseFragment : 데이터없음")
                }

                is NetworkResult.Success -> {
                    dressViewModel.get_dress_like_data()
                }
            }
        })

        storeViewModel.update_store_like_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis", "store_like_data, 데이터 없음")
                }

                is NetworkResult.Success -> {
                    storeViewModel.get_store_like_data()
                }
            }
        })

        initAppbar(binding.favoritebaseToolbar, "찜 목록", false, true)
        initViewPager()
    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var internalViewpagerAdapter = FavoriteBaseAdapter(this)
        internalViewpagerAdapter.addFragment(FavoriteClothFragment())
        internalViewpagerAdapter.addFragment(FavoriteStoreFragment())

        //Adapter 연결
        binding.favoriteViewpager.apply {
            adapter = internalViewpagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.favoriteTablayout, binding.favoriteViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "아이템"
                }
                1 -> {
                    tab.text = "스토어"
                }
            }
        }.attach()
    }
}



