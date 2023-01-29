package com.example.myapplication.ui.main

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.ui.main.chat.ChatFragment
import com.example.myapplication.ui.main.favorite.FavoriteFragment
import com.example.myapplication.ui.main.home.HomeBaseFragment
import com.example.myapplication.ui.main.location.LocationFragment
import com.example.myapplication.ui.main.profile.ProfileBlankFragment
import com.example.myapplication.utils.ApplicationClass
import com.example.myapplication.utils.ApplicationClass.Companion.KEY_SEARCH_HISTORY

class SecondActivity : AppCompatActivity() {
    // 바인딩
    private lateinit var binding: ActivitySecondBinding
    // 툴바
    private lateinit var activitytoolbar: androidx.appcompat.app.ActionBar
    // 툴바 검색 아이템
    var searchItem: MenuItem? = null
    // 툴바 알림 아이템
    var notificationItem: MenuItem? = null
    // 검색 기록을 저장하는 배열
    private var searchHistoryDataList = ArrayList<SearchHistroyData>()
    // 검색 기록 어댑터
    private lateinit var searchhistoryAdapter: SearchhistoryAdapter
    // 현재 보이는 fragment의 Tag를 저장
    private lateinit var currentFragmenttag: String

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

        // 전체 AppbarData 초기화
        initAppbar()
        // 네비게이션 버튼 클릭시 프래그먼트 전환
        binding.secondBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> { // 첫 번째 fragment
                    // 부분 AppbarData 초기화
                    if(supportFragmentManager.findFragmentByTag("homebase")?.childFragmentManager?.backStackEntryCount!! > 0){
                        var frag = supportFragmentManager.findFragmentByTag("homebase") as HomeBaseFragment
                        initPartAppbar(frag.appbarData.name, frag.appbarData.switch)
                    }else {
                        initPartAppbar("홈", false)
                    }
                    changeFragment("homebase", HomeBaseFragment())
                }
                R.id.menu_favorite -> { // 두 번째 fragment
                    initPartAppbar("찜", false)
                    changeFragment("favorite", FavoriteFragment())
                }
                R.id.menu_map -> { // 세 번째 fragment
                    initPartAppbar("주변매장", false)
                    changeFragment("location", LocationFragment())
                }
                R.id.menu_chat -> { // 세 번째 fragment
                    initPartAppbar("채팅", false)
                    changeFragment("chat", ChatFragment())
                }
                R.id.menu_profileblank -> {
                    initPartAppbar("마이페이지", false)
                    changeFragment("profileblank", ProfileBlankFragment())
                }
            }
            true
        }

        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("whatisthis","???")
            }
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

        // 쉐어드에서 데이터 가져오기
        searchHistoryDataList =
            ApplicationClass.sharedPreferencesmanager.getsearchhistoryString(KEY_SEARCH_HISTORY) as ArrayList<SearchHistroyData>

        // 기록을 보여줄 recycler의 어댑터, 어댑터 클릭 이벤트 처리
        searchhistoryAdapter = SearchhistoryAdapter(object : SearchhistoryAdapter.ItemClickListener{
            // recycler 아이템 중 텍스트를 클릭했을 때 -> 해당 텍스트로 재검색
            override fun onTextItemClick(view: View, position: Int) {

            }
            // recycler 아이템 중 x 이미지를 클릭했을 때 -> 데이터 삭제
            override fun onImageItemClick(view: View, position: Int) {
                searchHistoryDataList.removeAt(position)
                // 쉐어드 데이터를 덮어씌우는 것
                ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(KEY_SEARCH_HISTORY,searchHistoryDataList)
                // 데이터 변경 알리기
                searchhistoryAdapter.notifyDataSetChanged()
            }
        })

        // recycler의 어댑터 연결
        binding.secondRecycler.adapter = searchhistoryAdapter
        // 그리드 레이아웃으로 설정
        binding.secondRecycler.layoutManager = GridLayoutManager(applicationContext, 3)
        // 어댑터에 쉐어드 데이터 넣기
        searchhistoryAdapter.userList = searchHistoryDataList
        // 데이터 변경 알리기
        searchhistoryAdapter.notifyDataSetChanged()
    }

    // 전체 AppbarData 초기화 메소드
    private fun initAppbar() {
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
                // 메뉴 추가
                menuInflater.inflate(R.menu.menu_appbar, menu)
                // 메뉴 버튼(검색, 알림) visible
                searchItem = menu.findItem(R.id.search)
                notificationItem = menu.findItem(R.id.notification)
                searchItem?.setVisible(true)
                notificationItem?.setVisible(true)
                // searchView 선언
                var searchView = searchItem?.actionView as SearchView
                // searchView 힌트
                searchView.queryHint = "검색중"
                // searchView editText
                var searchViewEditText =
                    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                searchViewEditText.apply {
                    this.filters = arrayOf(InputFilter.LengthFilter(12))
                }
                // SearchView 가 Icon 화 되어 시작할지 펼쳐진 상태에서 시작할지 설정
                searchView.setIconifiedByDefault(false)

                // 검색 서비스
                var searchManager = getSystemService(SEARCH_SERVICE) as SearchManager


                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    // 검색 후 엔터를 쳤을 때
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // 글자를 하나라도 쳤을 때
                        if (!query.isNullOrEmpty()) {
                            val newSearchData = SearchHistroyData(query)
                            // 검색 기록 추가하기
                            searchHistoryDataList.add(newSearchData)
                            // 쉐어드에 저장하긱
                            ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(
                                KEY_SEARCH_HISTORY, searchHistoryDataList
                            )
                            // recycler 데이터 갱신 요청
                            searchhistoryAdapter.notifyDataSetChanged()
                            // 검색 결과 보여주기 (API 요청)

                        }else{
                            Toast.makeText(applicationContext, "글자를 입력하세요",Toast.LENGTH_SHORT).show()
                        }
                        return true
                    }

                    // 검색 중일 때
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })

                searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
                    when (hasFocus) {
                        true -> {
                            // 검색창에 커서가 생김
                            binding.secondSearchlayout.visibility = View.VISIBLE
                            binding.secondBottomNavigationView.visibility = View.INVISIBLE
                        }
                        false -> {
                            // 검색창에서 커서가 사라짐
                        }
                    }
                }


                searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                        // 검색창이 펼쳐 졌을 때
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                        // 검색창이 가려 졌을 때
                        binding.secondSearchlayout.visibility = View.INVISIBLE
                        binding.secondBottomNavigationView.visibility = View.VISIBLE
                        return true
                    }
                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        supportFragmentManager.findFragmentByTag(currentFragmenttag)?.childFragmentManager?.popBackStackImmediate(
                            null,
                            0
                        )
                        true
                    }
                    R.id.notification -> {
                        true
                    }
                    else -> false
                }
            }
        })
    }

    // 부분 초기화 메소드
    private fun initPartAppbar(name: String, switch: Boolean) {
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("whatisthis","???")
            }
        }
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