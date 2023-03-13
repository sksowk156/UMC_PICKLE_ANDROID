package com.example.myapplication.repository

import com.example.myapplication.data.remote.HomeService
import com.example.myapplication.data.remote.model.DressHomeDto
import com.example.myapplication.data.remote.model.DressOverviewDto
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class HomeRepository @Inject constructor(private val homeService: HomeService){

    suspend fun get_home_data(lat: Double, lng: Double): Flow<NetworkResult<DressHomeDto>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = homeService.get_home_data(lat, lng)
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

    suspend fun get_home_new_data(lat: Double, lng: Double) : Flow<NetworkResult<List<DressOverviewDto>>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response = homeService.get_home_new_data(lat, lng)
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

    suspend fun get_home_recent_data(): Flow<NetworkResult<List<DressOverviewDto>>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response = homeService.get_home_recent_data()
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