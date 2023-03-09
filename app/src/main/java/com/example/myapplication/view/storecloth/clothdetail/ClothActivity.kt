package com.example.myapplication.view.storecloth.clothdetail

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.repository.DressRepository
import com.example.myapplication.repository.HomeRepository
import com.example.myapplication.repository.StoreRepository
import com.example.myapplication.viewmodel.*
import com.example.myapplication.viewmodel.factory.HomeViewModelFactory
import com.example.myapplication.viewmodel.factory.StoreViewModelFactory

class ClothActivity : BaseActivity<ActivityClothBinding>(R.layout.activity_cloth) {
    lateinit var homeViewModel: HomeViewModel
    lateinit var storeViewModel: StoreViewModel
    lateinit var dressViewModel: DressViewModel

    override fun savedatainit() {
        val dressRepository = DressRepository()
        val homeRepository = HomeRepository()
        val storeRepository = StoreRepository()
        val homeViewModelProviderFactory = HomeViewModelFactory(homeRepository)
        val dressViewModelProviderFactory = DressViewModelFactory(dressRepository)
        val storeViewModelProviderFactory = StoreViewModelFactory(storeRepository)

        dressViewModel = ViewModelProvider(this, dressViewModelProviderFactory).get(DressViewModel::class.java)
        homeViewModel = ViewModelProvider(this,homeViewModelProviderFactory).get(HomeViewModel::class.java)
        storeViewModel = ViewModelProvider(this, storeViewModelProviderFactory).get(StoreViewModel::class.java)

        dressViewModel.get_dress_detail_data(intent.getIntExtra("cloth_id",0))

        supportFragmentManager
            .beginTransaction()
            .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
            .commitAllowingStateLoss()
    }

    override fun init() {
    }
}