package com.example.myapplication.ui.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAgreementBinding
import com.example.myapplication.db.remote.LoginService
import com.example.myapplication.db.remote.remotedata.AuthRequest
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.main.SecondActivity
import com.kakao.sdk.user.UserApiClient

class AgreementActivity : BaseActivity<ActivityAgreementBinding>(R.layout.activity_agreement) {

    override fun init() {
        binding.postbutton.setOnClickListener {

            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (token != null) {
                    val data = AuthRequest(token.accessToken)

                    LoginService.create(data)

                    Log.e(ContentValues.TAG, "토큰값 전송 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        Toast.makeText(
                            this,
                            "${user?.kakaoAccount?.profile?.nickname}님 환영합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }
    }
}
