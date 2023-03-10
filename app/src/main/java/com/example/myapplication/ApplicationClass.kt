package com.example.myapplication

import android.app.Application
import com.example.myapplication.widget.utils.ApiInstance
import com.example.myapplication.widget.utils.SharedPreferencesManager
import com.example.myapplication.widget.utils.Utils.KAKAO_APP_KEY
import com.example.myapplication.widget.utils.Utils.retrofit
import com.example.myapplication.widget.utils.Utils.sharedPreferencesmanager
import com.example.myapplication.widget.utils.XAccessTokenInterceptor
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesmanager = SharedPreferencesManager(applicationContext)
        retrofit = ApiInstance(XAccessTokenInterceptor(sharedPreferencesmanager)).init()
        // 카카오 로그인에 필요한 것
        KakaoSdk.init(this, KAKAO_APP_KEY)
    }
}