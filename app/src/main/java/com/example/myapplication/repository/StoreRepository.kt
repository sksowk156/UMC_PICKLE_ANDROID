package com.example.myapplication.repository

import com.example.myapplication.data.remote.StoreService
import com.example.myapplication.data.remote.model.StoreCoordDtoList
import com.example.myapplication.data.remote.model.StoreDetailDto
import com.example.myapplication.data.remote.model.StoreLikeDto
import com.example.myapplication.data.remote.model.UpdateStoreLikeDto
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class StoreRepository {
    suspend fun get_store_like_data(): Flow<NetworkResult<List<StoreLikeDto>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = StoreService.storeService.get_store_like_data()
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


    fun get_store_near_data(lat: Double, lng: Double) : Flow<NetworkResult<StoreCoordDtoList>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response = StoreService.storeService.get_store_near_data(lat, lng)
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

    fun get_store_detail_data(id: Int, category: String) : Flow<NetworkResult<StoreDetailDto>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response =  StoreService.storeService.get_store_detail_data(id, category)
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

    fun set_store_like_data(updateStoreLikeDto: UpdateStoreLikeDto) : Flow<NetworkResult<UpdateStoreLikeDto>> = flow{
        emit(NetworkResult.Loading())
        try {
            val response =  StoreService.storeService.set_store_like_data(updateStoreLikeDto)
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