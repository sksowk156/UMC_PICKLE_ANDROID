package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.remotedata.AuthRequest
import com.example.myapplication.data.remote.remotedata.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("auth/kakao")
    @Headers("accept: application/json", "content-type: application/json")
    suspend fun post_users(@Body jsonparams: AuthRequest): Response<AuthResponse>

}