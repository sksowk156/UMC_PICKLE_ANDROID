package com.example.myapplication.ui.store.clothdetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.MapViewModel

class ClothActivity : BaseActivity<ActivityClothBinding>(R.layout.activity_cloth) {
    lateinit var mapViewModel: MapViewModel

    override fun savedatainit() {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        supportFragmentManager
            .beginTransaction()
            .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
            .commitAllowingStateLoss()
    }

    override fun init() {
        mapViewModel.get_store_detail_data(intent.getIntExtra("cloth_id",0),"전체")
    }
}