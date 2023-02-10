package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.DressService
import com.example.myapplication.db.remote.model.DressDetailDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DressViewModel : ViewModel() {

    private var _dress_detail_data = MutableLiveData<DressDetailDto>()
    val dress_detail_data: LiveData<DressDetailDto> get() = _dress_detail_data

    fun get_dress_detail_data(id: Int) {
        DressService.dressService.get_dress_detail_data(id)
            .enqueue(object : Callback<DressDetailDto> {
                override fun onResponse(
                    call: Call<DressDetailDto>,
                    response: Response<DressDetailDto>
                ) {
                    if (response.isSuccessful) {
                        _dress_detail_data.value = (response.body())
                    } else {
                        _dress_detail_data.postValue(null)
                    }
                }

                override fun onFailure(call: Call<DressDetailDto>, t: Throwable) {
                    Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
                }
            })
    }
}