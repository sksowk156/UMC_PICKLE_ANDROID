package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.DressLikeDto
import com.example.myapplication.db.remote.model.HomeModel
import com.example.myapplication.db.remote.model.updatedressLikeDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import retrofit2.http.*

object DressLikeService {

    interface DressLikeInterface{

        //get post api interface
        @GET("dresses/likes/{id}")
        fun get_liked_dress_data(
            @Path("id") id:Int
        ):Call<DressLikeDto>

    }

    val dressLikeService = ApplicationClass.retrofit.create(DressLikeService.DressLikeInterface::class.java)


    fun get_liked_dress_data(id: Int){

        dressLikeService.get_liked_dress_data(id).enqueue(object : Callback<DressLikeDto> {
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


}