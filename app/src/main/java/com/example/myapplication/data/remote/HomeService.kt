package com.example.myapplication.data.remote

import com.example.myapplication.ApplicationClass
import com.example.myapplication.data.remote.model.DressHomeDto
import com.example.myapplication.data.remote.model.DressOverviewDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

object HomeService {

    interface HomeInterface {
        @GET("home")
        suspend fun get_home_data(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Response<DressHomeDto>

        @GET("home/new")
        suspend fun get_home_new_data(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Response<List<DressOverviewDto>>

        @GET("home/recent")
        suspend fun get_home_recent_data(
        ): Response<List<DressOverviewDto>>

        @GET("home/recommendation")
        suspend fun get_home_recommendation_data(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Response<List<DressOverviewDto>>
    }

    val homeService = ApplicationClass.retrofit.create(HomeService.HomeInterface::class.java)

}