package com.example.myapplication.ui

import android.Manifest
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.chat.ChatFragment
import com.example.myapplication.ui.main.favorite.FavoriteFragment
import com.example.myapplication.ui.main.home.HomeBaseFragment
import com.example.myapplication.ui.main.location.LocationFragment
import com.example.myapplication.ui.main.profile.ProfileBlankFragment
import kotlinx.android.synthetic.main.toolbar.view.*

class SecondActivity : AppCompatActivity() {

    private lateinit var activitytoolbar: androidx.appcompat.app.ActionBar

    var searchItem: MenuItem? = null
    var notificationItem: MenuItem? = null

    private lateinit var binding: ActivitySecondBinding
    private lateinit var currentFragmenttag: String // fragment의 Tag를 저장하기 위해

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar: Toolbar = binding.secondToolbar.toolbar
        setSupportActionBar(toolbar)

        // 앱을 켰을 때 첫 fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.secondFramelayout.id, HomeBaseFragment(), "homebase")
                .commitAllowingStateLoss()
            currentFragmenttag = "homebase" // 현재 보고 있는 fragmet의 Tag
        }

        // 전체 Appbar 초기화
        initAppbar()

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("whatisthis",query.toString())
            }
        }

        // 네비게이션 버튼 클릭시 프래그먼트 전환
        binding.secondBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> { // 첫 번째 fragment
                    // 부분 Appbar 초기화
                    initPartAppbar("홈",false)
                    changeFragment("homebase", HomeBaseFragment())
                }
                R.id.menu_favorite -> { // 두 번째 fragment
                    initPartAppbar("찜",false)
                    changeFragment("favorite", FavoriteFragment())
                }
                R.id.menu_map -> { // 세 번째 fragment
                    initPartAppbar("주변매장",false)
                    changeFragment("location", LocationFragment())
                }
                R.id.menu_chat -> { // 세 번째 fragment
                    initPartAppbar("채팅",false)
                    changeFragment("chat", ChatFragment())
                }
                R.id.menu_profileblank -> {
                    initPartAppbar("마이페이지",false)
                    changeFragment("profileblank", ProfileBlankFragment())
                }
            }
            true
        }

        if (!LocationPermissionUtils.isPermissionGranted(this)) {
            LocationPopupUtils.dialogLocationDisclosures(this,
                title = getString(R.string.title_location_disclosures),
                message = getString(R.string.msg_explanation_location_permission),
                getString(R.string.action_deny),
                getString(R.string.action_accept),
                onClickNeg = {
                    // Continue run app no permission.
                },
                onClickPos = {
                    requestLocationPermission()
                })
        } else {
            checkPermissionAndroidQ()
        }

    }

    // 전체 Appbar 초기화 메소드
    private fun initAppbar(){
        // MenuProvider를 사용할 때
        supportActionBar?.apply {
            activitytoolbar = this
        }
        // 뒤로가기 버튼 추가
        activitytoolbar.setDisplayHomeAsUpEnabled(false)
        // 툴바 제목 변경
        activitytoolbar.setTitle("홈")
        // 메뉴 적용
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                // Add menu items here
                menuInflater.inflate(R.menu.menu_appbar, menu)
                searchItem = menu.findItem(R.id.search)
                notificationItem = menu.findItem(R.id.notification)
                searchItem?.setVisible(true)
                notificationItem?.setVisible(true)
                // searchView 기능
                var searchView = searchItem?.actionView as SearchView
                var searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        Log.d("whatisthis", "11")
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        Log.d("whatisthis", "22")
                        return true
                    }
                })
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        supportFragmentManager.findFragmentByTag(currentFragmenttag)?.childFragmentManager?.popBackStackImmediate(null, 0)
                        true
                    }
                    R.id.notification -> {
                        Log.d("whatisthis", "4")
                        true
                    }
                    else -> false
                }
            }
        })
    }
    // 부분 초기화 메소드
    private fun initPartAppbar(name:String, switch : Boolean){
        // 뒤로가기 버튼 추가
        activitytoolbar.setDisplayHomeAsUpEnabled(switch)
        // 툴바 제목 변경
        activitytoolbar.setTitle(name)
        // item 가리기
        if (searchItem != null) {
            searchItem?.setVisible(!switch)
        }
        if (notificationItem != null) {
            notificationItem?.setVisible(!switch)
        }
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

    //위치 권한
    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            var isGranted = true
            permissions.entries.forEach {
                if (it.value == false) {
                    isGranted = false
                    return@registerForActivityResult
                }
            }
            if (isGranted) {
                // Check background permission android Q
                checkPermissionAndroidQ()
            } else {
                // Continue run app no permission.
            }

        }

    private fun requestLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        requestLocationPermissionLauncher.launch(permissions)
    }

    private val requestPermissionAndroidQ =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { _: Boolean ->
            // We just receive action when user close screen setting background mode.
            // Continue run app flow
        }

    private fun checkPermissionAndroidQ() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (LocationPermissionUtils.isBackgroundLocationGranted(this)) {
                // Continue run app flow
            } else {
                LocationPermissionUtils.openSettingBackgroundMode(requestPermissionAndroidQ)
            }

        } else {
            // Continue run app flow
        }
    }


}