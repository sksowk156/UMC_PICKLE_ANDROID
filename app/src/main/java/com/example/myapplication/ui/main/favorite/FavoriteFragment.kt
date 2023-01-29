package com.example.myapplication.ui.main.favorite

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.base.AppbarData
import com.example.myapplication.ui.base.BaseFragment


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    lateinit var appbarData: AppbarData

    override fun init() {
        changeAppbar()
    }
    fun changeAppbar(){
        initAppbar("찜", false)
    }
}
