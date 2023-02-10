package com.example.myapplication.db.remote

import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.StoreCoordDtoList
import com.example.myapplication.db.remote.model.StoreDetailDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object StoreService { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.

    interface StoreInterface {
        @GET("stores/near")
        fun get_store_near_data(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Call<StoreCoordDtoList>

        @GET("stores/detail/{id}")
        fun get_store_detail_data(
            @Path(value = "id") id: Int,
            @Query("category") category: String
        ): Call<StoreDetailDto>

    }
    val storeService = ApplicationClass.retrofit.create(StoreService.StoreInterface::class.java)
}