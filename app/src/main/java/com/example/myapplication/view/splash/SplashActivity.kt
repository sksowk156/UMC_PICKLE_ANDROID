package com.example.myapplication.view.splash

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ApplicationClass.Companion.username
import com.example.myapplication.view.login.MainActivity
import com.example.myapplication.view.main.SecondActivity
import com.kakao.sdk.user.UserApiClient

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if(error!=null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else if(tokenInfo != null){
                    UserApiClient.instance.me { user, error ->
                        username = user?.kakaoAccount?.profile?.nickname.toString()
                        Toast.makeText(this, "${username}님 환영합니다.", Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1000) // 시간 0.5초 이후 실행
    }

}