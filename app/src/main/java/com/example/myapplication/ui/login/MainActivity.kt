package com.example.myapplication.ui.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.db.remote.APIS
import com.example.myapplication.ui.*
import com.example.myapplication.ui.main.SecondActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val api = APIS.create();
    lateinit var viewbinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        KakaoSdk.init(this, "e9c2a8bf10ae12652fdc9ee9059ac02f")

        viewbinding.button.setOnClickListener{
            // 카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e(TAG, "로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        Log.e(TAG, "로그인 성공! 토큰값 : ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            Log.e(TAG, "닉네임 ${user?.kakaoAccount?.profile?.nickname}")
                            Log.e(TAG, "이메일 ${user?.kakaoAccount?.email}" )
                        }

                        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                            if (token != null) {
                                val data = PostModel(token.accessToken)
                                api.post_users(data).enqueue(object : Callback<PostResult> {
                                    override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                                        Log.d("log", response.toString())
                                        Log.d("log", response.body().toString())
                                    }

                                    override fun onFailure(call: Call<PostResult>, t: Throwable) {
                                        // 실패
                                        Log.d("log", t.message.toString())
                                        Log.d("log", "fail")
                                    }
                                })
                                Log.e(ContentValues.TAG, "토큰값 전송 완료 ${token.accessToken}")
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
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
            }
        }

    }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (token != null) {
            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AgreementActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }
}