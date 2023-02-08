package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.MapService
import com.example.myapplication.db.remote.model.MapModel
import com.example.myapplication.db.remote.model.StoreDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {

    private var _near_store_data = MutableLiveData<MapModel>()
    val near_store_data: LiveData<MapModel> get() = _near_store_data

    private var _near_store_detail = MutableLiveData<StoreDetailData>()
    val near_store_detail: LiveData<StoreDetailData> get() = _near_store_detail

    fun near_store(lat: Double, lng: Double) {
        MapService.mapService.near_store(lat, lng).enqueue(object : Callback<MapModel> {
            override fun onResponse(call: Call<MapModel>, response: Response<MapModel>) {
                if (response.isSuccessful) {
                    _near_store_data.postValue(response.body())
                } else {
                    _near_store_data.postValue(null)
                }
            }

            override fun onFailure(call: Call<MapModel>, t: Throwable) {
                Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                _near_store_data.postValue(null)
            }
        })
    }

    fun near_store_detail(id: Int, category: String) {
        MapService.mapService.near_store_detail(id, category)
            .enqueue(object : Callback<StoreDetailData> {
                override fun onResponse(
                    call: Call<StoreDetailData>,
                    response: Response<StoreDetailData>
                ) {
                    if (response.isSuccessful) {
                        _near_store_detail.postValue(response.body())
                    } else {
                        _near_store_detail.postValue(null)
                    }
                }

                override fun onFailure(call: Call<StoreDetailData>, t: Throwable) {
                    Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                    _near_store_detail.postValue(null)
                }
            })
    }

}