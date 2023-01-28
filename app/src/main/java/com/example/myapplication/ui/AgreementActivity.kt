package com.example.myapplication.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.myapplication.databinding.ActivityAgreementBinding
import com.kakao.sdk.user.UserApiClient

class AgreementActivity : AppCompatActivity() {

    val binding by lazy { ActivityAgreementBinding.inflate(layoutInflater) }
    val api = APIS.create();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.postbutton.setOnClickListener {

            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (token != null) {
                    val data = PostModel(token.accessToken)
                    api.post_users(data).enqueue(object : Callback<PostResult> {
                        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                            Log.d("log", response.toString())
                            Log.d("log", response.body().toString())
                            Log.d("log", response.body()?.isNewMember.toString())
                        }

                        override fun onFailure(call: Call<PostResult>, t: Throwable) {
                            // 실패
                            Log.d("log", t.message.toString())
                            Log.d("log", "fail")
                        }
                    })
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
