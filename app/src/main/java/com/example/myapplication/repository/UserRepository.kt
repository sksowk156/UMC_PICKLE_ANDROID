package com.example.myapplication.repository

import com.example.myapplication.data.remote.UserService
import com.example.myapplication.data.remote.model.ResultOfSetDto
import com.example.myapplication.data.remote.model.UserProfileDto
import com.example.myapplication.data.remote.model.UserProfileEditDto
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class UserRepository {
    suspend fun get_user_profile_data(): Flow<NetworkResult<UserProfileDto>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = UserService.userService.get_user_profile_data()
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

    suspend fun set_user_profile_data(userUpdateDtoDto: UserProfileEditDto): Flow<NetworkResult<ResultOfSetDto>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response =  UserService.userService.set_user_profile_data(userUpdateDtoDto)
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