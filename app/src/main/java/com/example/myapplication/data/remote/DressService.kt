package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.model.DressLikeDto
import com.example.myapplication.data.remote.model.DressDetailDto
import com.example.myapplication.data.remote.model.DressSearchDto
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.widget.utils.Utils.retrofit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

object DressService {

    interface DressInterface{
        // 의상에 대한 좋아요 정보 요청
        @GET("dresses/like-list")
        suspend fun get_dress_like_data(
        ): Response<List<DressLikeDto>>

        // 한 의상에 대한 좋아요 정보 수정
        @POST("dresses/like")
        suspend fun set_dress_like_data(
            @Body updateDressLikeDto: UpdateDressLikeDto
        ): Response<UpdateDressLikeDto>

        // 한 의상에 대한 상세 정보 요청
        @GET("dresses/detail/{dress_id}")
        suspend fun get_dress_detail_data(
            @Path("dress_id") id : Int
        ):Response<DressDetailDto>

        // 한 의상에 대한 상세 정보 요청
        @GET("dresses/search")
        suspend fun get_dress_search_data(
            @Query("category") category: String,
            @Query("latitude") lat: Double,
            @Query("longitude") lng: Double,
            @Query("name") name: String,
            @Query("sort") sort: String
            ):Response<DressSearchDto>

        //의상 예약 내역 조회
        @GET("dresses/order-list")
        suspend fun get_dress_order_data(
            @Query("status") status: String
        ):Response<List<DressOrderListDto>>

        //의상 예약 상세 내역 조회
        @GET("dresses/orders/{dress_reservation_id}")
        suspend fun get_dress_order_detail_data(
            @Path("dress_reservation_id")dress_reservation_id:Int
        ):Response<List<DressOrderDto>>

        // 의상 예약
        @POST("dresses/reservation")
        suspend fun set_dress_reservation(
            @Body dressReservationDto: DressReservationDto
        ): Response<ResultOfSetDto>

        // 의상 예약폼 받기
        @GET("dresses/reservation/{store_id}")
        suspend fun get_dress_reservation_store_data(
            @Path("store_id") store_id: Int
        ): Response<DressReservationFormDto>

        @POST("dresses/reservation/cancel/{reservation_id}")
        suspend fun set_dress_reservation_cancel(
            @Path("reservation_id") reservation_id: Int
        ): Response<ResultOfSetDto>
    }

    val dressService = retrofit.create(DressService.DressInterface::class.java)
}