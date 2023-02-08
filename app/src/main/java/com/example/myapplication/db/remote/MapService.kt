package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.MapModel
import com.example.myapplication.db.remote.model.StoreDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object MapService { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.

    interface MapInterface {
        @GET("stores/near")
        fun near_store(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
        ): Call<MapModel>

        @GET("stores/detail/{id}")
        fun near_store_detail(
            @Path(value = "id") id: Int,
            @Query("category") category: String
        ): Call<StoreDetailData>

    }
    val mapService = ApplicationClass.retrofit.create(MapService.MapInterface::class.java)

    fun near_store(lat: Double, lng: Double){
        var data : MapModel ?= null
        mapService.near_store(lat, lng).enqueue(object : Callback<MapModel> {
            override fun onResponse(call: Call<MapModel>, response: Response<MapModel>) {
                data = response.body()!!
            }
            override fun onFailure(call: Call<MapModel>, t: Throwable) {
                Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
            }
        })
    }

    fun near_store_detail(id: Int,category: String){
        var data : StoreDetailData ?= null
        mapService.near_store_detail(id,category).enqueue(object : Callback<StoreDetailData> {
            override fun onResponse(call: Call<StoreDetailData>, response: Response<StoreDetailData>) {
                data = response.body()
            }

            override fun onFailure(call: Call<StoreDetailData>, t: Throwable) {
                Log.d("whatisthis", "네트워크 오류가 발생했습니다." + t.message.toString())
            }
        })
    }


}