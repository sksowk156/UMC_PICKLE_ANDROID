package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface StoreService { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.

    @GET("stores/near")
    suspend fun get_store_near_data(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Response<StoreCoordDtoList>

    @GET("stores/detail/{store_id}")
    suspend fun get_store_detail_data(
        @Path(value = "store_id") id: Int,
        @Query("category") category: String
    ): Response<StoreDetailDto>

    @GET("stores/like-list")
    suspend fun get_store_like_data(
    ): Response<List<StoreLikeDto>>

    // 한 의상에 대한 좋아요 정보 수정
    @POST("stores/like")
    suspend fun set_store_like_data(
        @Body updateStoreLikeDto: UpdateStoreLikeDto
    ): Response<UpdateStoreLikeDto>

}