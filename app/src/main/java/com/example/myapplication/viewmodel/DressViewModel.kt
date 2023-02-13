package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.remote.DressService
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.db.remote.model.UpdateDressLikeDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DressViewModel : ViewModel() {

    private var _dress_detail_data = MutableLiveData<DressDetailDto>()
    val dress_detail_data: LiveData<DressDetailDto> get() = _dress_detail_data

    private var _dress_like_data = MutableLiveData<List<DressLikeDto>>()
    val dress_like_data: LiveData<List<DressLikeDto>> get() = _dress_like_data

    private var _dress_search_data = MutableLiveData<DressDetailDto>()
    val dress_search_data: LiveData<DressDetailDto> get() = _dress_search_data

    private var _update_dress_like_data = MutableLiveData<UpdateDressLikeDto>()
    val update_dress_like_data: LiveData<UpdateDressLikeDto> get() = _update_dress_like_data

    fun get_dress_detail_data(id: Int) {
        DressService.dressService.get_dress_detail_data(id).enqueue(object : Callback<DressDetailDto> {
                override fun onResponse(
                    call: Call<DressDetailDto>,
                    response: Response<DressDetailDto>
                ) {
                    if (response.isSuccessful) {
                        _dress_detail_data.value = (response.body())
                    } else {
                        _dress_detail_data.postValue(null)
                        Log.d("whatisthis","_dress_detail_data, response 못받음")
                    }
                }

                override fun onFailure(call: Call<DressDetailDto>, t: Throwable) {
                    Log.d("whatisthis", "get_dress_detail_data, 네트워크 오류가 발생했습니다." + t.message.toString())
                }
            })
    }

    fun set_dress_like_data(updateDressLikeDto: UpdateDressLikeDto){
        DressService.dressService.set_dress_like_data(updateDressLikeDto).enqueue(object :
            Callback<UpdateDressLikeDto> {
            override fun onResponse(call: Call<UpdateDressLikeDto>, response: Response<UpdateDressLikeDto>) {
                if(response.isSuccessful) {
                    _update_dress_like_data.value = response.body()
                }else{
                    Log.d("whatisthis","좋아요 실패")
                }
            }

            override fun onFailure(call: Call<UpdateDressLikeDto>, t: Throwable) {
                Log.d("whatisthis","set_dress_like_data, 네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

    fun get_dress_like_data(){
        DressService.dressService.get_dress_like_data().enqueue(object : Callback<List<DressLikeDto>> {
            override fun onResponse(call: Call<List<DressLikeDto>>, response: Response<List<DressLikeDto>>) {
                if(response.isSuccessful) {
                    _dress_like_data.postValue(response.body())
                }else{
                    _dress_like_data.postValue(null)
                    Log.d("whatisthis","_dress_like_data, response 못받음")
                }
            }

            override fun onFailure(call: Call<List<DressLikeDto>>, t: Throwable) {
                Log.d("whatisthis","get_dress_like_data, 네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

    fun get_dress_search_data(category: String,lat: Double,lng: Double,name: String,sort: String) {
        DressService.dressService.get_dress_search_data("전체",lat,lng,name,"좋아요많은순").enqueue(object : Callback<DressDetailDto> {
            override fun onResponse(
                call: Call<DressDetailDto>,
                response: Response<DressDetailDto>
            ) {
                if (response.isSuccessful) {
                    _dress_detail_data.value = (response.body())
                } else {
                    _dress_detail_data.postValue(null)
                    Log.d("whatisthis","_dress_search_data, response 못받음")
                }
            }

            override fun onFailure(call: Call<DressDetailDto>, t: Throwable) {
                Log.d("whatisthis", "get_dress_search_data, 네트워크 오류가 발생했습니다." + t.message.toString())
            }
        })
    }

}