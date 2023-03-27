package com.example.myapplication.base

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
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.myapplication.R
import com.example.myapplication.widget.utils.LocationPermissionUtils
import com.example.myapplication.widget.utils.LocationPopupUtils
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var locatioNManager: LocationManager
    var lat_lng: Pair<Double, Double>? = null


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
    }

    protected fun requestLocationData() {
        if (!LocationPermissionUtils.isPermissionGranted(this)) {
            permissionAskDialog()
        } else {
            getLocation()
        }
    }

    private fun permissionAskDialog(){
        LocationPopupUtils.dialogLocationDisclosures(this,
            title = getString(R.string.title_location_disclosures),
            message = getString(R.string.msg_explanation_location_permission),
            getString(R.string.action_deny),
            getString(R.string.action_accept),
            onClickNeg = {
                // Continue run app no permission.
            },
            onClickPos = {
                requestLocationPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            })
    }

    protected fun requestLocationPermission() {
        requestLocationPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    //위치 권한
    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isGranted = true

        permissions.entries.forEach { permission ->
            when {
                permission.value -> {
                }
                shouldShowRequestPermissionRationale(permission.key) -> {
                    Toast.makeText(this, "내 주변 매장 옷들을 추천받을려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                    // 권한이 필요한 이유 설명 후, 한번 더 권한 물어보기
                    isGranted = false
                    return@registerForActivityResult
                }
                else -> {
                    isGranted = false
                    // 세팅으로 넘기기(무조건 필요한 경우에만)
                }
            }
        }

        if (isGranted) {
            getLocation()
            // Check background permission android Q
//                checkPermissionAndroidQ()
        }
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

    private val requestPermissionAndroidQ = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean ->
        // We just receive action when user close screen setting background mode.
        // Continue run app flow
    }

    protected fun getLocation() {
        locatioNManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var userLocation: Location = getLatLng()
        lat_lng = Pair(userLocation.latitude, userLocation.longitude)
//            latitude = userLocation.latitude
//            longitude = userLocation.longitude
//            Log.d("CheckCurrentLocation", "현재 내 위치 값: ${latitude}, ${longitude}")
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

    private fun getLatLng(): Location {
        var currentLatLng: Location? = null
        var hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )
        var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            val locatioNProvider = LocationManager.GPS_PROVIDER
//            val isGPSEnabled =
//                locatioNManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//            val isNetworkEnabled =
//                locatioNManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            currentLatLng = locatioNManager.getLastKnownLocation(locatioNProvider)
                ?: locatioNManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    REQUIRED_PERMISSIONS[0]
//                )
//            ) {
//                Toast.makeText(this, "앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
//                ActivityCompat.requestPermissions(
//                    this,
//                    REQUIRED_PERMISSIONS,
//                    PERMISSIONS_REQUEST_CODE
//                )
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    REQUIRED_PERMISSIONS,
//                    PERMISSIONS_REQUEST_CODE
//                )
//            }
        }
        return currentLatLng!!
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.isNotEmpty()) {
//            for (i in grantResults.indices) {
//                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                    Snackbar.make(binding.root, "권한에 동의하지 않을 경우 이용할 수 없습니다.", Snackbar.LENGTH_SHORT)
//                        .show()
//                    return
//                }
//            }
//            Snackbar.make(binding.root, "위치 권한이 동의 되었습니다.", Snackbar.LENGTH_SHORT).show()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}