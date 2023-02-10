package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.MapService
import com.example.myapplication.db.remote.model.MapModel
import com.example.myapplication.db.remote.model.StoreDetailDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {

    private var _store_near_data = MutableLiveData<MapModel>()
    val store_near_data: LiveData<MapModel> get() = _store_near_data

    private var _store_detail_data = MutableLiveData<StoreDetailDto>()
    val store_detail_data: LiveData<StoreDetailDto> get() = _store_detail_data

    fun get_store_near_data(lat: Double, lng: Double) {
        MapService.mapService.get_store_near_data(lat, lng).enqueue(object : Callback<MapModel> {
            override fun onResponse(call: Call<MapModel>, response: Response<MapModel>) {
                if (response.isSuccessful) {
                    _store_near_data.postValue(response.body())
                } else {
                    _store_near_data.postValue(null)
                }
            }

            override fun onFailure(call: Call<MapModel>, t: Throwable) {
                Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                _store_near_data.postValue(null)
            }
        })
    }

    fun get_store_detail_data(id: Int, category: String) {
        MapService.mapService.get_store_detail_data(id, category)
            .enqueue(object : Callback<StoreDetailDto> {
                override fun onResponse(
                    call: Call<StoreDetailDto>,
                    response: Response<StoreDetailDto>
                ) {
                    if (response.isSuccessful) {
                        _store_detail_data.postValue(response.body())
                    } else {
                        _store_detail_data.postValue(null)
                    }
                }

                override fun onFailure(call: Call<StoreDetailDto>, t: Throwable) {
                    Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                    _store_detail_data.postValue(null)
                }
            })
    }

}