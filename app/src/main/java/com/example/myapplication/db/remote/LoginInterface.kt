package com.example.myapplication.db.remote

import com.example.myapplication.db.remote.remotedata.LoginTokenAccessData
import com.example.myapplication.db.remote.remotedata.LoginTokenResultData
import com.example.myapplication.db.remote.remotedata.PostModel
import com.example.myapplication.db.remote.remotedata.PostResult
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {

    @POST("api/auth/kakao")
    @Headers("accept: application/json", "content-type: application/json")
    fun post_users(@Body jsonparams: PostModel): Call<PostResult>

    @POST("api/auth/join")
    fun getjwt(@Body user: LoginTokenAccessData): Call<LoginTokenResultData>

}