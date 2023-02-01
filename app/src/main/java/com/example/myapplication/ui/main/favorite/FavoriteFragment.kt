package com.example.myapplication.ui.main.favorite

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.home.NewFragment
import com.example.myapplication.ui.main.home.RecentFragment


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun init() {
        binding.tabStore.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.favorite_base_layout, FavoriteStoreFragment(),"favoritestore")
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.tabItem.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.favorite_base_layout, FavoriteItemFragment(),"favoriteItem")
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }
}
