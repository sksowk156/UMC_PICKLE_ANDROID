package com.example.myapplication.ui.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAgreementBinding
import com.example.myapplication.db.remote.LoginService
import com.example.myapplication.db.remote.remotedata.PostModel
import com.example.myapplication.ui.main.SecondActivity
import com.kakao.sdk.user.UserApiClient

class AgreementActivity : AppCompatActivity() {

    val binding by lazy { ActivityAgreementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.postbutton.setOnClickListener {

            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (token != null) {
                    val data = PostModel(token.accessToken)

                    LoginService.create(data)

                    Log.e(ContentValues.TAG, "토큰값 전송 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        Toast.makeText(this, "${user?.kakaoAccount?.profile?.nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }
    }
}
