package com.example.myapplication.ui.main.location.map

import android.graphics.Color
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.location.around.AroundFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import com.naver.maps.map.widget.LogoView

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun init() {
        initMap()

        // 플로팅 버튼 클릭시 이벤트 처리
        binding.mapFab.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.location_layout, AroundFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    private fun initMap(){
        // 지도 띄우기
        val fm = childFragmentManager
        val mapFragment =
            fm.findFragmentById(R.id.map) as com.naver.maps.map.MapFragment?
                ?: com.naver.maps.map.MapFragment.newInstance().also {
                    fm
                        .beginTransaction()
                        .add(R.id.map, it)
                        .commitAllowingStateLoss()
                }

        mapFragment.getMapAsync(this)

        // 지도 옵션 초기화
        initializePersistentBottomSheet()
    }

    private fun initializePersistentBottomSheet() {

        val dm = resources.displayMetrics
        // BottomSheetBehavior에 layout 설정
        bottomSheetBehavior = BottomSheetBehavior.from(binding.mapBottomsheetlayout)
        // 미리보기 높이 = 현재 위치 버튼 크기 + 로고 크기 + 마진 크기
        bottomSheetBehavior.peekHeight =
            Math.round((24 + LocationButtonView.MEASURED_HEIGHT_STATE_SHIFT + LogoView.MEASURED_HEIGHT_STATE_SHIFT) * dm.density)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                // BottomSheetBehavior state에 따른 이벤트
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    // 네이버 map api
    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.mapType = NaverMap.MapType.Basic

        // 확대 축소
        naverMap.maxZoom = 18.0 // 확대
        naverMap.minZoom = 12.0 // 축소

        // 지도 옵션 설정
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true) // 대중교통 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true) // 건물 표시
        naverMap.isIndoorEnabled = true // 실내지도 표시


        // 초기 위치 설정
        val cameraUpdate =
            CameraUpdate.scrollTo((LatLng(37.4979921, 127.028046))).animate(CameraAnimation.Easing)
        // 제일 처음 LatLng(37.5666102, 126.9783881)에서 한번 대기하고, 그 다음에 초기 설정한 위치 LatLng(37.4979921, 127.028046)로 이동한다.
        naverMap.moveCamera(cameraUpdate)

        // ui 설정
        val uiSettings = naverMap.uiSettings

        uiSettings.isIndoorLevelPickerEnabled = true // 실내 지도 층수 버튼
        uiSettings.isZoomControlEnabled = true // 확대 축소 버튼
        uiSettings.isCompassEnabled = true  // 나침반 버튼 -> 위치를 변경하기 위해서
        uiSettings.isLogoClickEnabled = true // 로고 버튼 -> 이건 무조건 해줘야 하나봄
        uiSettings.logoGravity = (Gravity.RIGHT) // 로고 위치
        uiSettings.setLogoMargin(4, 4, 4, 4) // 로고 마진

        uiSettings.isLocationButtonEnabled = false // 현재 위치 버튼 -> 위치를 변경하기 위해서

        // 위치버튼 갱신
        binding.mapLocationbutton.map = naverMap

        naverMap.locationTrackingMode = LocationTrackingMode.Face

        // 현재 위치 설정
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        // 카메라가 이동하기 전 화면의 위도 경도 값을 저장할 변수
        var before_LatLngBounds: LatLngBounds

        // 최초 바운더리에서 최초로 보여지는 Marker들을 추가한다.
        // 지금 바운더리로 새롭게 데이터 요청 *************
//        // Retrofit 주변 매장 전체 데이터 가져와서 데이터 담기
//        marketaroundservice.marketList().enqueue(object : Callback<MarketAroundDataList> {
//            override fun onResponse(
//                call: Call<MarketAroundDataList>,
//                response: Response<MarketAroundDataList>
//            ) {
//                response.body()?.let { result ->
//                    marketAroundDataList = result
//                }
//            }
//
//            override fun onFailure(call: Call<MarketAroundDataList>, t: Throwable) {
//                Log.e("에러에러", "${t.localizedMessage}")
//            }
//        })

        // 요청 후 Retrofit 데이터로 Marker 표시하기************
//        marketAroundDataList.forEach {
//            // Marker 생성
//            val marker = Marker()
//            marker.position = LatLng(it.latitude, it.longitude) // 위도, 경도에 존재하는 Marker 만들기
//            marker.tag =
//                it.id // Marker의 tag에 가게들을 구분가능하게 해주는 primaryKey를 저장한다. -> 클릭 시 찾기 위함
//            marker.icon = MarkerIcons.BLACK
//            marker.iconTintColor = Color.RED
//            marker.captionText = it.market_around_name // Marker 이름을 가게 이름으로 하기
//            marker.isHideCollidedSymbols = true // 마커와 겹치는 지도 심벌을 자동으로 숨기도록 지정하는 예제
//            marker.map = naverMap
//            before_markerList.add(marker)
//        }

        // 최초 바운더리 저장
        before_LatLngBounds = naverMap.contentBounds

        var marker_temp: Marker
        var position_temp: LatLng
        // 화면 움직임이 멈췄을 때 -> 화면에 보이는 부분의 데이터 받아오기
        naverMap.addOnCameraIdleListener {
            // 지금 바운더리에서 새롭게 보여지는 Marker들은 추가한다.
            // 지금 바운더리로 새롭게 데이터 요청 *************
//            // Retrofit 주변 매장 전체 데이터 가져와서 데이터 담기
//            marketaroundservice.marketList().enqueue(object : Callback<MarketAroundDataList> {
//                override fun onResponse(
//                    call: Call<MarketAroundDataList>,
//                    response: Response<MarketAroundDataList>
//                ) {
//                    response.body()?.let { result ->
//                        marketAroundDataList = result
//                    }
//                }
//
//                override fun onFailure(call: Call<MarketAroundDataList>, t: Throwable) {
//                    Log.e("에러에러", "${t.localizedMessage}")
//                }
//            })

            // 요청 후 Retrofit 데이터로 Marker 표시하기************
//            marketAroundDataList.forEach {
//                // Marker 생성
//                val marker = Marker()
//                marker.position = LatLng(it.latitude, it.longitude) // 위도, 경도에 존재하는 Marker 만들기
//                marker.tag =
//                    it.id // Marker의 tag에 가게들을 구분가능하게 해주는 primaryKey를 저장한다. -> 클릭 시 찾기 위함
//                marker.icon = MarkerIcons.BLACK
//                marker.iconTintColor = Color.RED
//                marker.captionText = it.market_around_name // Marker 이름을 가게 이름으로 하기
//                marker.isHideCollidedSymbols = true // 마커와 겹치는 지도 심벌을 자동으로 숨기도록 지정하는 예제
//                marker.map = naverMap
//                now_markerList.add(marker)
//            }

//            for (i in 0..(now_markerList.size - 1)) { // 지금 바운더리 Marker들 전부 확인
//                marker_temp = before_markerList[i]
//                position_temp = marker_temp.position
//                if (!before_LatLngBounds.contains(position_temp)) { // 이전 바운더리에서 안보이던 지금 바운더리의 Marker는 추가한다.
//                    marker_temp.map = naverMap
//                }
//                // 지금 바운더리에서도 여전히 보이는 이전 바운더리 Marker는 보존한다.
//            }
//
//            for (i in 0..(before_markerList.size - 1)) { // 이전 바운더리 Marker들 전부 확인
//                marker_temp = before_markerList[i]
//                position_temp = marker_temp.position
//                if (!naverMap.contentBounds.contains(position_temp)) { // 지금 바운더리에서 안보여지는 이전 바운더리의 Marker는 삭제한다.
//                    marker_temp.map = null
//                }
//                // 이전 바운더리에서도 존재하고 있던 지금 바운더리 Marker는 보존한다.
//            }
//
//            before_markerList = now_markerList // 이전 Marker 데이터 갱신
//            before_LatLngBounds = naverMap.contentBounds // 이전 바운더리 데이터 갱신
        }

        // 전체 데이터를 들고와서 Marker를 표시할 경우
//        marketAroundDataList.forEach {
//            // Marker 생성
//            val marker = Marker()
//            marker.position = LatLng(it.latitude, it.longitude) // 위도, 경도에 존재하는 Marker 만들기
//            marker.tag =
//                it.id // Marker의 tag에 가게들을 구분가능하게 해주는 primaryKey를 저장한다. -> 클릭 시 찾기 위함
//            marker.icon = MarkerIcons.BLACK
//            marker.iconTintColor = Color.RED
//            marker.captionText = it.market_around_name // Marker 이름을 가게 이름으로 하기
//            marker.isHideCollidedSymbols = true // 마커와 겹치는 지도 심벌을 자동으로 숨기도록 지정하는 예제
//            marker.map = naverMap
//            now_markerList.add(marker)
//        }

        // 임시 Marker 생성
        val marker = Marker()
        marker.minZoom = 13.3 // Marker가 보이는 최대 줌 정하기
        marker.position = LatLng(37.4979921, 127.028046)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED
        marker.captionText = "something"
        marker.isHideCollidedSymbols = true // 마커와 겹치는 지도 심벌을 자동으로 숨기도록 지정하는 예제

        // Marker 클릭시
        marker.setOnClickListener { overlay ->

//            // Retrofit 주변매장 데이터를 가져왔을 경우 -> 클릭한 Marker의 정보를 List 데이터에서 tag로 찾는다.
//            val market : MarketAroundData = marketAroundDataList.firstOrNull{
//                it.id = overlay.tag
//            }
//            // 위에서 찾았다면 데이터 Bottomsheet에 들어갈 데이터 갱신
//            binding.marketName.text = market.market_around_name
//            binding.marketAddress.text = market.market_around_address
//            binding.marketOperationhours.text = market.market_around_operationhours

            // bottomsheetbehavior 사용할 경우
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

//            // bottomsheetdialogfragment 사용할 경우
//            val bottomSheet = MarketBottomFragment()
//            bottomSheet.show(childFragmentManager, bottomSheet.tag)
            true
        }

        // Marker가 아닌 바깥 지도 클릭시
        naverMap.setOnMapClickListener { pointF, latLng ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

//        // 마커를 표시할 때 쓰는 쓰레드 ???
//        val executor: Executor = ...
//        val handler = Handler(Looper.getMainLooper())
//
//        executor.execute {
//            // 백그라운드 스레드
//            val markers = mutableListOf<Marker>()
//
//            repeat(1000) {
//                markers += Marker().apply {
//                    position = ...
//                    icon = ...
//                    captionText = ...
//                }
//            }
//
//            handler.post {
//                // 메인 스레드
//                markers.forEach { marker ->
//                    marker.map = naverMap
//                }
//            }
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}