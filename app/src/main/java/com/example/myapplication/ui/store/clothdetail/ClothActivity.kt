package com.example.myapplication.ui.store.clothdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityClothBinding

class ClothActivity : AppCompatActivity() {
    // 바인딩
    private lateinit var binding: ActivityClothBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앱을 켰을 때 첫 fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.clothLayout.id, ClothBlankFragment(), "clothblank")
                .commitAllowingStateLoss()
        }
    }
}