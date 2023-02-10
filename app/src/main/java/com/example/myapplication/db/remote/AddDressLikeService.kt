package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.DressLikeService.dressLikeService
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.db.remote.model.updatedressLikeDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


object AddDressLikeService {

    interface AddDressLikeInterface{
        @FormUrlEncoded
        @POST("dresses/likes")
        fun add_liked_dress_data(
            @Body UpdatedressLikeDto: updatedressLikeDto
        ): Call<updatedressLikeDto>
    }

    val AddDressLikeService = ApplicationClass.retrofit.create(AddDressLikeService.AddDressLikeInterface::class.java)


    fun add_liked_dress_data(UpdatedressLikeDto: updatedressLikeDto){

        AddDressLikeService.add_liked_dress_data(UpdatedressLikeDto).enqueue(object : Callback<updatedressLikeDto> {
            override fun onResponse(call: Call<updatedressLikeDto>, response: Response<updatedressLikeDto>) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    Log.d("isitsuccessful","data:\n"+resp.toString())
                }else{
                    Log.d("fail","실패")
                }
            }

            override fun onFailure(call: Call<updatedressLikeDto>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

}