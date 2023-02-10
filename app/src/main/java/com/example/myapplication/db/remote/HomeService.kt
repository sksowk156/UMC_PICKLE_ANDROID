package com.example.myapplication.db.remote

import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.HomeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

object HomeService {

    interface HomeInterface {
        @GET("home")
        fun get_home_data(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Call<HomeModel>
    }

    val homeService = ApplicationClass.retrofit.create(HomeService.HomeInterface::class.java)


}