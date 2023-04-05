package com.example.myapplication.widget.utils

import android.util.Log
import com.example.myapplication.widget.utils.Utils.X_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class XAccessTokenInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferencesManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = sharedPreferences.getJwt(X_ACCESS_TOKEN)
        // 서버로 부터 발급 받은 토큰 값이 preference에 저장되어 있다면 retrofit 쓸 때마다 자동으로 헤더에 넣어준다.
        jwtToken?.let {
            builder.addHeader("Authorization", jwtToken ?: "")
        }

        return chain.proceed(builder.build())
    }
}