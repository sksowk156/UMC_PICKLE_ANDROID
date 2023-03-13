package com.example.myapplication.view.storecloth.clothdetail

import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClothActivity : BaseActivity<ActivityClothBinding>(R.layout.activity_cloth) {
    val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    val storeViewModel: StoreViewModel by viewModels<StoreViewModel>()
    val dressViewModel: DressViewModel by viewModels<DressViewModel>()

    override fun savedatainit() {
        dressViewModel.get_dress_detail_data(intent.getIntExtra("cloth_id",0))

        supportFragmentManager
            .beginTransaction()
            .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
            .commitAllowingStateLoss()
    }

    override fun init() {
    }
}