package com.example.myapplication

import android.app.Application
import com.example.myapplication.widget.config.XAccessTokenInterceptor
import com.example.myapplication.widget.utils.SharedPreferencesManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        const val SERVER_TOKEN: String = "server_token" // jwt 저장하는 sharedpref를 찾을 때 필요한 key값
        const val X_ACCESS_TOKEN: String = "x_access_token" // jwt 저장하는 sharedpref에서 jwt 찾을 때 필요한 key값
        const val SHARED_SEARCH_HISTORY = "shared_search_histroy" // 검색기록을 저장하는 sharedpref를 찾을 때 필요한 key값
        const val KEY_SEARCH_HISTORY = "key_search_history" // 검색기록을 저장하는 sharedpref에서 검색기록을 찾을 때 필요한 key값

        const val BASE_URL = "https://pick-cle.shop/api/" // API 의 base_url

        lateinit var sharedPreferencesmanager: SharedPreferencesManager // 쉐어드
        lateinit var username: String
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        // 한번만 생성한다.
        sharedPreferencesmanager = SharedPreferencesManager(applicationContext)

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // preference에 저장된 jwt 토큰을 헤더에 자동으로 입력
            .build()

        // ??
        val gson: Gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // 카카오 로그인에 필요한 것
        KakaoSdk.init(this, "e9c2a8bf10ae12652fdc9ee9059ac02f")

    }
}