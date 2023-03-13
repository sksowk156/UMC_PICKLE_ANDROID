package com.example.myapplication

import android.app.Application
import com.example.myapplication.widget.utils.Utils.KAKAO_APP_KEY
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        // 카카오 로그인에 필요한 것
        KakaoSdk.init(this, KAKAO_APP_KEY)
    }
}