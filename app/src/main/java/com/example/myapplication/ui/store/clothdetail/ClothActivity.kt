package com.example.myapplication.ui.store.clothdetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding
import com.example.myapplication.ui.base.BaseActivity

class ClothActivity : BaseActivity<ActivityClothBinding>(R.layout.activity_cloth) {

    override fun savedatainit() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
            .commitAllowingStateLoss()
    }

    override fun init() {

    }
}