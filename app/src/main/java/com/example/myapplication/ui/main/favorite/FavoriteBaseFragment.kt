package com.example.myapplication.ui.main.favorite


import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteBaseBinding
import com.example.myapplication.ui.base.BaseFragment


class FavoriteBaseFragment : BaseFragment<FragmentFavoriteBaseBinding>(R.layout.fragment_favorite_base) {

    override fun init() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.favorite_base_layout, FavoriteFragment(), "favorite")
            .commitAllowingStateLoss()
        initAppbar(binding.favoriteBaseToolbarcontent,binding.favoriteBaseToolbar,"찜 목록")

    }

   /* fun changeAppbar() {
        childFragmentManager.addOnBackStackChangedListener {
            when (childFragmentManager.fragments.last().tag) {
                "favoriteItem" -> {
                    initSubAppbar("찜", true, false)
                }
                "favoriteStore" -> {
                    initSubAppbar("찜", true, false)
                }
                "favorite" -> {
                    initSubAppbar("찜", false, true)
                }
            }
        }
    }*/


}