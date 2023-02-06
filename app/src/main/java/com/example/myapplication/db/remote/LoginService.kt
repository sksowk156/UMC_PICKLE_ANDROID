package com.example.myapplication.db.remote

import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.ApplicationClass.Companion.retrofit
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
                ApplicationClass.sharedPreferencesmanager.setJwt(ApplicationClass.X_ACCESS_TOKEN, resp?.appToken.toString())

            }
            override fun onFailure(call: Call<PostResult>, t: Throwable) {
                Log.d("whatisthis","네트워크 오류가 발생했습니다."+ t.message.toString())
            }
        })
    }

}