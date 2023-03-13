package com.example.myapplication.view.login

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAgreementBinding
import com.example.myapplication.data.remote.remotedata.AuthRequest
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.viewmodel.LoginViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementActivity : BaseActivity<ActivityAgreementBinding>(R.layout.activity_agreement) {
    val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.postbutton.setOnClickListener {

            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (token != null) {
                    val data = AuthRequest(token.accessToken)

                    loginViewModel.create(data)

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
