package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.StoreService
import com.example.myapplication.db.remote.model.StoreCoordDtoList
import com.example.myapplication.db.remote.model.StoreDetailDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {

    private var _store_near_data = MutableLiveData<StoreCoordDtoList>()
    val store_near_data: LiveData<StoreCoordDtoList> get() = _store_near_data

    private var _store_detail_data = MutableLiveData<StoreDetailDto>()
    val store_detail_data: LiveData<StoreDetailDto> get() = _store_detail_data

    fun get_store_near_data(lat: Double, lng: Double) {
        StoreService.storeService.get_store_near_data(lat, lng).enqueue(object : Callback<StoreCoordDtoList> {
            override fun onResponse(call: Call<StoreCoordDtoList>, response: Response<StoreCoordDtoList>) {
                if (response.isSuccessful) {
                    _store_near_data.postValue(response.body())
                } else {
                    _store_near_data.postValue(null)
                }
            }

            override fun onFailure(call: Call<StoreCoordDtoList>, t: Throwable) {
                Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                _store_near_data.postValue(null)
            }
        })
    }

    fun get_store_detail_data(id: Int, category: String) {
        StoreService.storeService.get_store_detail_data(id, category)
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