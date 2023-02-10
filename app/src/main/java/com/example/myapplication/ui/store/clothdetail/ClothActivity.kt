package com.example.myapplication.ui.store.clothdetail

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.StoreViewModel

class ClothActivity : BaseActivity<ActivityClothBinding>(R.layout.activity_cloth) {
    lateinit var dressViewModel: DressViewModel

    override fun savedatainit() {
        dressViewModel = ViewModelProvider(this).get(DressViewModel::class.java)
        dressViewModel.get_dress_detail_data(intent.getIntExtra("cloth_id",0))

        supportFragmentManager
            .beginTransaction()
            .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
            .commitAllowingStateLoss()
    }

    override fun init() {
    }
}