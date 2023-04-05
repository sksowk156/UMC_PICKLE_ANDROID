package com.example.myapplication.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.widget.utils.LocationManager
import com.example.myapplication.widget.utils.LocationPermissionUtils
import com.example.myapplication.widget.utils.LocationPopupUtils

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!
    private lateinit var locationManager: LocationManager

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var locatioNManager: LocationManager
    var lat_lng = MutableLiveData<Pair<Double, Double>>()


    protected open fun savedatainit() {}
    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            savedatainit()
        }
        init()

        if(::locationManager.isInitialized){
            locationManager.latlng.observe(this, Observer {
                lat_lng.value = Pair(it.latitude, it.longitude)
            })
        }
    }

    protected fun requestLocationData() {
        if (!isAllPermissionsGranted()) { // 위치 권한 없을 때(foreground이므로 background 제외)
            permissionAskDialog() // 처음 물어볼 때는 background도 물어보자
        } else { // 위치 권한 있을 때(foreground이므로 background 제외)
            // 위치 정보를 바탕으로 데이터 요청
            getLocationData()
        }
    }

    private fun permissionAskDialog() {
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
    }

    // 위치 정보 요청
    private fun getLocationData() {
      // background x -> 앱을 켰을 때만
            locationManager = LocationManager(this, 1800000L, 1f)
            locationManager.startLocationTracking()

    }

    private fun isAllPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestLocationPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    //위치 권한
    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isGranted = true
        permissions.entries.forEach { permission ->
            when {
                permission.value -> {}
                ActivityCompat.shouldShowRequestPermissionRationale(this, permission.key) -> {
                    Toast.makeText(this, "내 주변 질문들을 볼 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                    // 권한이 필요한 이유 설명 후, 한번 더 권한 물어보기
                    isGranted = false
                    return@registerForActivityResult
                }
                else -> isGranted = false
                // 세팅으로 넘기기(무조건 필요한 경우에만)
            }
        }

        if (isGranted) {
            getLocationData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}