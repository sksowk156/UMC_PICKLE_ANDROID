package com.example.myapplication.repository

import com.example.myapplication.data.remote.DressService
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class DressRepository {
    suspend fun get_dress_detail_data(id: Int): Flow<NetworkResult<DressDetailDto>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = DressService.dressService.get_dress_detail_data(id)
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


    suspend fun set_dress_like_data(updateDressLikeDto: UpdateDressLikeDto) : Flow<NetworkResult<UpdateDressLikeDto>> = flow {
        try {
            val response = DressService.dressService.set_dress_like_data(updateDressLikeDto)
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

    suspend fun get_dress_like_data() : Flow<NetworkResult<List<DressLikeDto>>> = flow {
        try {
            val response = DressService.dressService.get_dress_like_data()
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


    //의상 주문 상세 내역 조회
    suspend fun get_dress_order_detail_data(dress_reservation_id: Int) : Flow<NetworkResult<List<DressOrderDto>>> = flow {
        try {
            val response = DressService.dressService.get_dress_order_detail_data(dress_reservation_id)
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



    suspend fun get_dress_order_data(status: String) : Flow<NetworkResult<List<DressOrderListDto>>> = flow{
        try {
            val response = DressService.dressService.get_dress_order_data(status)
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


    suspend fun get_dress_search_data(category: String,lat: Double,lng: Double,name: String,sort: String) : Flow<NetworkResult<DressSearchDto>> = flow {
        try {
            val response = DressService.dressService.get_dress_search_data(category,lat,lng,name,sort)
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

    suspend fun set_dress_reservation(dressReservationDto: DressReservationDto) : Flow<NetworkResult<ResultOfSetDto>> = flow{
        try {
            val response = DressService.dressService.set_dress_reservation(dressReservationDto)
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

    suspend fun get_dress_reservation_store_data(id: Int) : Flow<NetworkResult<DressReservationFormDto>> = flow{
        try {
            val response = DressService.dressService.get_dress_reservation_store_data(id)
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

    suspend fun set_dress_reservation_cancel(reservation_id: Int) : Flow<NetworkResult<ResultOfSetDto>> = flow{
        try {
            val response = DressService.dressService.set_dress_reservation_cancel(reservation_id)
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