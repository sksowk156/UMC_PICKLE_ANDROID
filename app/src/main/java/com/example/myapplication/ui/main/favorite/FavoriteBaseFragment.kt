package com.example.myapplication.ui.main.favorite

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBaseBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.favorite.item.FavoriteItemFragment
import com.example.myapplication.ui.main.favorite.store.FavoriteStoreFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteBaseFragment : BaseFragment<FragmentFavoriteBaseBinding>(R.layout.fragment_favorite_base) {
    lateinit var dressViewModel: DressViewModel
    lateinit var storeViewModel: StoreViewModel

    override fun init() {
        dressViewModel = ViewModelProvider(this).get(DressViewModel::class.java)
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        dressViewModel.get_dress_like_data()
        storeViewModel.get_store_like_data()

        initAppbar(binding.favoritebaseToolbar,"찜 목록",false,true)
        initViewPager()
    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var internalViewpagerAdapter = FavoriteBaseAdapter(this)
        internalViewpagerAdapter.addFragment(FavoriteItemFragment())
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



