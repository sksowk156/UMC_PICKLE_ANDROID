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
        fun get_liked_dress_data(
            @Path("id") id:Int
        ): Call<DressLikeDto>

        // 한 의상에 대한 좋아요 정보 수정
        @POST("dresses/likes")
        fun set_liked_dress_data(
            @Body UpdatedressLikeDto: UpdateDressLikeDto
        ): Call<UpdateDressLikeDto>

        // 한 의상에 대한 상세 정보 요청
        @GET("dresses/detail/{id}")
        fun get_dress_detail_data(
            @Path("id") id : Int
        ):Call<DressDetailDto>
    }

    val dressService = ApplicationClass.retrofit.create(DressService.DressInterface::class.java)

    fun set_liked_dress_data(UpdatedressLikeDto: UpdateDressLikeDto){
        DressService.dressService.set_liked_dress_data(UpdatedressLikeDto).enqueue(object :
            Callback<UpdateDressLikeDto> {
            override fun onResponse(call: Call<UpdateDressLikeDto>, response: Response<UpdateDressLikeDto>) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    Log.d("isitsuccessful","data:\n"+resp.toString())
                }else{
                    Log.d("fail","실패")
                }
            }

            override fun onFailure(call: Call<UpdateDressLikeDto>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

    fun get_liked_dress_data(id: Int){
        dressService.get_liked_dress_data(id).enqueue(object : Callback<DressLikeDto> {
            override fun onResponse(call: Call<DressLikeDto>, response: Response<DressLikeDto>) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    Log.d("isitsuccessful","data:\n"+resp.toString())
                }else{
                    Log.d("fail","실패")
                }
            }

            override fun onFailure(call: Call<DressLikeDto>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

    fun get_dress_detail_data(id: Int){
        dressService.get_dress_detail_data(id).enqueue(object : Callback<DressDetailDto> {
            override fun onResponse(call: Call<DressDetailDto>, response: Response<DressDetailDto>) {
                if(response.isSuccessful) {
                    val resp = response.body()
                    Log.d("isitsuccessful","data:\n"+resp.toString())
                }else{
                    Log.d("fail","실패")
                }
            }

            override fun onFailure(call: Call<DressDetailDto>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }


}