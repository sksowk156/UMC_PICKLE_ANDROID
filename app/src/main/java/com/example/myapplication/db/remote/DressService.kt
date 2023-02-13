package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.db.remote.model.UpdateDressLikeDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object DressService {

    interface DressInterface{
        // 한 의상에 대한 좋아요 정보 요청
        @GET("dresses/likes/{id}")
        fun get_dress_like_data(
        ): Call<List<DressLikeDto>>

        // 한 의상에 대한 좋아요 정보 수정
        @POST("dresses/likes")
        fun set_dress_like_data(
            @Body updateDressLikeDto: UpdateDressLikeDto
        ): Call<UpdateDressLikeDto>

        // 한 의상에 대한 상세 정보 요청
        @GET("dresses/detail/{id}")
        fun get_dress_detail_data(
            @Path("id") id : Int
        ):Call<DressDetailDto>
    }

    val dressService = ApplicationClass.retrofit.create(DressService.DressInterface::class.java)
}