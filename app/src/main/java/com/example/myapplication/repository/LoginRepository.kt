package com.example.myapplication.repository


import com.example.myapplication.data.remote.LoginService
import com.example.myapplication.data.remote.remotedata.AuthRequest
import com.example.myapplication.data.remote.remotedata.AuthResponse
import com.example.myapplication.widget.utils.NetworkResult
import com.example.myapplication.widget.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class LoginRepository() {

    suspend fun create(jsonparams: AuthRequest): Flow<NetworkResult<AuthResponse>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = LoginService.authService.post_users(jsonparams)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                try {
                    emit(NetworkResult.Error(response.errorBody()!!.string()))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: ""))
        } as Unit
    }.flowOn(Dispatchers.IO)

}