package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.HomeService
import com.example.myapplication.db.remote.model.DressHomeDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var _home_latlng = MutableLiveData<Pair<Double,Double>>()
    val home_latlng: LiveData<Pair<Double,Double>> get() = _home_latlng

    private var _home_data = MutableLiveData<DressHomeDto>()
    val home_data: LiveData<DressHomeDto> get() = _home_data

    init {
        _home_latlng.value = Pair(37.5581, 126.9260)
    }

    fun set_home_latlng(lat:Double,lng:Double){
        _home_latlng.value = Pair(lat, lng)
    }

    fun get_home_data(lat: Double, lng: Double) {
        HomeService.homeService.get_home_data(lat, lng).enqueue(object : Callback<DressHomeDto> {
            override fun onResponse(call: Call<DressHomeDto>, response: Response<DressHomeDto>) {
                if (response.isSuccessful) {
                    _home_data.postValue(response.body())
                } else {
                    _home_data.postValue(null)
                    Log.d("whatisthis", "_home_data, response 못받음")
                }
            }

            override fun onFailure(call: Call<DressHomeDto>, t: Throwable) {
                Log.d("whatisthis", "get_home_data, 네트워크 오류가 발생했습니다." + t.message.toString())
            }
        })
    }
}