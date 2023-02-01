package com.example.myapplication

import android.app.Application
import com.example.myapplication.db.local.SharedPreferencesManager
import com.kakao.sdk.common.KakaoSdk

class ApplicationClass : Application() {
    companion object {
        const val SHARED_SEARCH_HISTORY = "shared_search_histroy"
        const val KEY_SEARCH_HISTORY = "key_search_history"
        //        const val ISSUE_API_URL = api 베이스 주소
        // 만들어져있는 SharedPreferences 를 사용해야한다.
        lateinit var sharedPreferencesmanager: SharedPreferencesManager

//        // JWT Token Header 키 값
//        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

//        // Issue 요청 key
//        val Issue_request_key = "키값"

//        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용
//        lateinit var IssueRetrofit: Retrofit
        lateinit var username : String
    }

    override fun onCreate() {
        super.onCreate()
//        sSharedPreferences =
//            applicationContext.getSharedPreferences("SOFTSQUARED_TEMPLATE_APP", MODE_PRIVATE)
        sharedPreferencesmanager = SharedPreferencesManager(applicationContext)

        KakaoSdk.init(this, "e9c2a8bf10ae12652fdc9ee9059ac02f")

    }
}