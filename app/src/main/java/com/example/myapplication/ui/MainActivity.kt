package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.main.chat.ChatFragment
import com.example.myapplication.ui.main.favorite.FavoriteFragment
import com.example.myapplication.ui.main.home.HomeFragment
import com.example.myapplication.ui.main.home.SliderAdapter
import com.example.myapplication.ui.main.location.LocationFragment
import com.example.myapplication.ui.main.profile.ProfileFragment
import com.smarteist.autoimageslider.SliderView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragmenttag: String // fragment의 Tag를 저장하기 위해

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 앱을 켰을 때 첫 fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.mainFramelayout.id, HomeFragment(), "home")
                .commitAllowingStateLoss()
            currentFragmenttag = "home" // 현재 보고 있는 fragmet의 Tag
        }


        // 네비게이션 버튼 클릭시 프래그먼트 전환
        binding.mainBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> { // 첫 번째 fragment
                    changeFragment("home", HomeFragment())
                }
                R.id.menu_favorite -> { // 두 번째 fragment
                    changeFragment("favorite", FavoriteFragment())
                }
                R.id.menu_map -> { // 세 번째 fragment
                    changeFragment("location", LocationFragment())
                }
                R.id.menu_chat -> { // 세 번째 fragment
                    changeFragment("chat", ChatFragment())
                }
                R.id.menu_profile -> {
                    changeFragment("profile", ProfileFragment())
                }
            }
            true
        }

    }

    private fun changeFragment(tag: String, fragment: Fragment) {
        // supportFragmentManager에 "first"라는 Tag로 저장된 fragment 있는지 확인
        if (supportFragmentManager.findFragmentByTag(tag) == null) { // Tag가 없을 때 -> 없을 리가 없다.
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(currentFragmenttag)!!)
                .add(binding.mainFramelayout.id, fragment, tag)
                .commitAllowingStateLoss()
        } else { // Tag가 있을 때
            // 먼저 currentFragmenttag에 저장된 '이전 fragment Tag'를 활용해 이전 fragment를 hide 시킨다.
            // supportFragmentManager에 저장된 "first"라는 Tag를 show 시킨다.
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(currentFragmenttag)!!)
                .show(supportFragmentManager.findFragmentByTag(tag)!!)
                .commitAllowingStateLoss()
        }
        // currentFragmenttag에 '현재 fragment Tag' "first"를 저장한다.
        currentFragmenttag = tag
    }

}