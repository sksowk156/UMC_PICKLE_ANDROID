package com.example.myapplication.widget.utils

import com.example.myapplication.ApplicationClass.Companion.BASE_URL
import com.example.myapplication.widget.config.XAccessTokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiInstance(private val interceptor: Interceptor) {

    lateinit var retrofit: Retrofit

    val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30000, TimeUnit.MILLISECONDS)
        .connectTimeout(30000, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor(interceptor) // preference에 저장된 jwt 토큰을 헤더에 자동으로 입력
        .build()

    // ??
    val gson: Gson = GsonBuilder().setLenient().create()

    fun init() : Retrofit{
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

}