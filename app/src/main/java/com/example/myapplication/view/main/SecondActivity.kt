package com.example.myapplication.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.repository.DressRepository
import com.example.myapplication.repository.HomeRepository
import com.example.myapplication.repository.StoreRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.view.main.chat.ChatFragment
import com.example.myapplication.view.main.favorite.FavoriteBaseFragment
import com.example.myapplication.view.main.home.HomeBaseFragment
import com.example.myapplication.view.main.location.LocationFragment
import com.example.myapplication.view.main.profile.ProfileBlankFragment
import com.example.myapplication.viewmodel.*
import com.example.myapplication.viewmodel.factory.HomeViewModelFactory
import com.example.myapplication.viewmodel.factory.StoreViewModelFactory

class SecondActivity : BaseActivity<ActivitySecondBinding>(R.layout.activity_second) {
    lateinit var homeViewModel: HomeViewModel
    lateinit var dressViewModel: DressViewModel
    lateinit var storeViewModel: StoreViewModel
    lateinit var userViewModel: UserViewModel

    private lateinit var currentFragmenttag: String

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmenttag = savedInstanceState.get("currentfragment").toString()
    }

    override fun savedatainit() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.secondFramelayout.id, HomeBaseFragment(), "homebase")
            .commitAllowingStateLoss()
        currentFragmenttag = "homebase" // 현재 보고 있는 fragmet의 Tag
    }

    override fun onResume() {
        super.onResume()
        dressViewModel.get_dress_like_data()
        storeViewModel.get_store_like_data()
        // 홈화면 데이터 갱신
        requestLocationData()
    }

    override fun init() {
        val dressRepository = DressRepository()
        val homeRepository = HomeRepository()
        val storeRepository = StoreRepository()
        val userRepository = UserRepository()

        val dressViewModelProviderFactory = DressViewModelFactory(dressRepository)
        val homeViewModelProviderFactory = HomeViewModelFactory(homeRepository)
        val storeViewModelProviderFactory = StoreViewModelFactory(storeRepository)
        val userViewModelProviderFactory = UserViewModelFactory(userRepository)

        dressViewModel = ViewModelProvider(this, dressViewModelProviderFactory).get(DressViewModel::class.java)
        homeViewModel = ViewModelProvider(this, homeViewModelProviderFactory).get(HomeViewModel::class.java)
        storeViewModel = ViewModelProvider(this, storeViewModelProviderFactory).get(StoreViewModel::class.java)
        userViewModel = ViewModelProvider(this, userViewModelProviderFactory).get(UserViewModel::class.java)

        userViewModel.get_user_profile_data()

        // 네비게이션 버튼의 테마색으로 변하는 것을 막기 위해서
        binding.secondBottomNavigationView.itemIconTintList = null

        // 네비게이션 버튼 클릭시 프래그먼트 전환
        binding.secondBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeBaseFragment -> { // 첫 번째 fragment
                    changeFragment("homebase", HomeBaseFragment())
                }
                R.id.favoriteBaseFragment -> { // 두 번째 fragment
                    changeFragment("favorite", FavoriteBaseFragment())
                }
                R.id.locationFragment -> { // 세 번째 fragment
                    changeFragment("location", LocationFragment())
                }
                R.id.chatFragment -> { // 세 번째 fragment
                    changeFragment("chat", ChatFragment())
                }
                R.id.profileBlankFragment -> {
                    changeFragment("profileblank", ProfileBlankFragment())
                }
            }
            true
        }

        getLocation()
        // 홈화면 데이터 갱신
        homeViewModel.home_latlng.observe(this, Observer<Pair<Double, Double>> {
            if (it != null) {
                homeViewModel.get_home_data(it!!.first, it!!.second)
            } else {
                Log.d("whatisthis", "home_latlng, 데이터 없음")
            }
        })
    }

    private fun changeFragment(tag: String, fragment: Fragment) {
        // supportFragmentManager에 "first"라는 Tag로 저장된 fragment 있는지 확인
        if (supportFragmentManager.findFragmentByTag(tag) == null) { // Tag가 없을 때 -> 없을 리가 없다.
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(currentFragmenttag)!!)
                .add(binding.secondFramelayout.id, fragment, tag)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentfragment", currentFragmenttag)
    }
}