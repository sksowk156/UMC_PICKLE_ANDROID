package com.example.myapplication.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySecondBinding
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.main.chat.ChatFragment
import com.example.myapplication.ui.main.favorite.FavoriteBaseFragment
import com.example.myapplication.ui.main.home.HomeBaseFragment
import com.example.myapplication.ui.main.location.LocationFragment
import com.example.myapplication.ui.main.profile.ProfileBlankFragment
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.MapViewModel

class SecondActivity :BaseActivity<ActivitySecondBinding>(R.layout.activity_second) {
    private var latitude: Double=0.0
    private var longitude:Double=0.0
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var locatioNManager : LocationManager
    private lateinit var currentFragmenttag: String

    lateinit var homeViewModel: HomeViewModel

    override fun savedatainit() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        supportFragmentManager
            .beginTransaction()
            .add(binding.secondFramelayout.id, HomeBaseFragment(), "homebase")
            .commitAllowingStateLoss()
        currentFragmenttag = "homebase" // 현재 보고 있는 fragmet의 Tag
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentfragment",currentFragmenttag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmenttag = savedInstanceState.get("currentfragment").toString()
    }

    override fun init() {

        // 네비게이션 버튼의 테마색으로 변하는 것을 막기 위해서
        binding.secondBottomNavigationView.itemIconTintList = null

        // 네비게이션 버튼 클릭시 프래그먼트 전환
        binding.secondBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> { // 첫 번째 fragment
                    changeFragment("homebase", HomeBaseFragment())
                }
                R.id.menu_favorite -> { // 두 번째 fragment
                    changeFragment("favorite", FavoriteBaseFragment())
                }
                R.id.menu_map -> { // 세 번째 fragment
                    changeFragment("location", LocationFragment())
                }
                R.id.menu_chat -> { // 세 번째 fragment
                    changeFragment("chat", ChatFragment())
                }
                R.id.menu_profileblank -> {
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
//            checkPermissionAndroidQ()
            getLocation()
        }

        Log.d("whatisthis",latitude.toString()+" "+longitude.toString())

        homeViewModel.get_home_data(37.5581, 126.9260)

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
                getLocation()
                // Check background permission android Q
//                checkPermissionAndroidQ()
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

    private fun getLocation(){
        locatioNManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var userLocation: Location = getLatLng()

        if(userLocation != null){
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("CheckCurrentLocation", "현재 내 위치 값: ${latitude}, ${longitude}")
//            var mGeoCoder =  Geocoder(applicationContext, Locale.KOREAN)
//            var mResultList: List<Address>? = null
//            try{
//                mResultList = mGeoCoder.getFromLocation(
//                    latitude!!, longitude!!, 1
//                )
//            }catch(e: IOException){
//                e.printStackTrace()
//            }
//
//            if(mResultList != null){
//                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
//            }
        }
    }

    private fun getLatLng(): Location{

        var currentLatLng: Location? = null
        var hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ||
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
            val locatioNProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locatioNManager?.getLastKnownLocation(locatioNProvider)
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])){
                Toast.makeText(this, "앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }
            currentLatLng = getLatLng()
        }
        return currentLatLng!!
    }

}