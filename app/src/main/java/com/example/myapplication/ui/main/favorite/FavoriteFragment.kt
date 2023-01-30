package com.example.myapplication.ui.main.favorite

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.base.BaseFragment


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun init() {
        initAppbar(binding.favoriteToolbarcontent,binding.favoriteToolbar,"찜")
    }
}
