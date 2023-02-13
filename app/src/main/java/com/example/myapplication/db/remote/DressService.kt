package com.example.myapplication.db.remote

import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

        //의상 예약 내역 조회
        @GET("dresses/order-list")
        fun get_dress_resevation_data(
            @Query("status") status: String
        ):Call<DressOrderDto>

        //의상 예약 상세 내역 조회
        @GET("dresses/orders/{dress_reservation_id}")
        fun get_dress_reservation_dress_data(
            @Path("dress_reservation_id")dress_reservation_id:Int
        ):Call<DressOrderDto>



    }




    val dressService = ApplicationClass.retrofit.create(DressService.DressInterface::class.java)
}