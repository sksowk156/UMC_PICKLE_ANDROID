package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.ApplicationClass.Companion.retrofit
import com.example.myapplication.db.remote.remotedata.LoginTokenAccessData
import com.example.myapplication.db.remote.remotedata.LoginTokenResultData
import com.example.myapplication.db.remote.remotedata.PostModel
import com.example.myapplication.db.remote.remotedata.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginService{ // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.

    fun create(jsonparams: PostModel){
        val authService = retrofit.create(LoginInterface::class.java)

        authService.post_users(jsonparams).enqueue(object : Callback<PostResult> {
            override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                val resp = response.body()
                Log.d("whatisthis","카카오 appToken :"+ resp?.appToken.toString() + " 카카오 isNewMember:"+  resp?.isNewMember.toString())
            }
            override fun onFailure(call: Call<PostResult>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

    fun jwt(accessData: LoginTokenAccessData) {
        val authService = retrofit.create(LoginInterface::class.java)

        authService.getjwt(accessData).enqueue(object : Callback<LoginTokenResultData> {
            override fun onResponse(call: Call<LoginTokenResultData>, response: Response<LoginTokenResultData>) {
                val resp = response.body()
                Log.d("whatisthis",response.code().toString())
                if(resp?.token != null){
                    // 토큰 저장
                    Log.d("whatisthis",resp.token.toString())
                    ApplicationClass.sharedPreferencesmanager.setJwt(ApplicationClass.X_ACCESS_TOKEN, resp.token!!)
                }else{
                    Log.d("whatisthis","토큰이 비었습니다.")
                }
            }
            override fun onFailure(call: Call<LoginTokenResultData>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다.")
            }
        })
    }

}