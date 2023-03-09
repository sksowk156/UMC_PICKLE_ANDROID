package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.repository.DressRepository
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.launch

class DressViewModel(private val dressRepository: DressRepository) : ViewModel() {

    private var _dress_detail_data = MutableLiveData<NetworkResult<DressDetailDto>>()
    val dress_detail_data: LiveData<NetworkResult<DressDetailDto>> get() = _dress_detail_data

    private var _dress_like_data = MutableLiveData<NetworkResult<List<DressLikeDto>>>()
    val dress_like_data: LiveData<NetworkResult<List<DressLikeDto>>> get() = _dress_like_data

    private var _update_dress_like_data = MutableLiveData<NetworkResult<UpdateDressLikeDto>>()
    val update_dress_like_data: LiveData<NetworkResult<UpdateDressLikeDto>> get() = _update_dress_like_data

    private var _dress_order_detail_data = MutableLiveData<NetworkResult<List<DressOrderDto>>>()
    val dress_order_detail_data: LiveData<NetworkResult<List<DressOrderDto>>> get() = _dress_order_detail_data

    private var _dress_order_data = MutableLiveData<NetworkResult<List<DressOrderListDto>>>()
    val dress_order_data: LiveData<NetworkResult<List<DressOrderListDto>>> get() = _dress_order_data

    private var _dress_search_data = MutableLiveData<NetworkResult<DressSearchDto>>()
    val dress_search_data: LiveData<NetworkResult<DressSearchDto>> get() = _dress_search_data

    private var _update_dress_reservation_data = MutableLiveData<NetworkResult<ResultOfSetDto>>()
    val update_dress_reservation_data: LiveData<NetworkResult<ResultOfSetDto>> get() = _update_dress_reservation_data

    private var _dress_reservation_store_data = MutableLiveData<NetworkResult<DressReservationFormDto>>()
    val dress_reservation_store_data: LiveData<NetworkResult<DressReservationFormDto>> get() = _dress_reservation_store_data

    private var _dress_reservation_cancel = MutableLiveData<NetworkResult<ResultOfSetDto>>()
    val dress_reservation_cancel: LiveData<NetworkResult<ResultOfSetDto>> get() = _dress_reservation_cancel

    private var _completeorder = MutableLiveData<Int>()
    val completeorder: LiveData<Int> get() = _completeorder
    private var _pickup = MutableLiveData<Int>()
    val pickup: LiveData<Int> get() = _pickup
    private var _pickupconfirm = MutableLiveData<Int>()
    val pickupconfirm: LiveData<Int> get() = _pickupconfirm
    private var _purchaseconfirm = MutableLiveData<Int>()
    val purchaseconfirm: LiveData<Int> get() = _purchaseconfirm

    fun set_completeorder(size:Int){
        _completeorder.value = size
    }

    fun set_pickup(size:Int){

        _pickup.value = size
    }

    fun set_pickupconfirm(size:Int){

        _pickupconfirm.value = size
    }

    fun set_purchaseconfirm(size:Int){
        _purchaseconfirm.value = size
    }

     fun get_dress_detail_data(id: Int) {
        viewModelScope.launch {
            dressRepository.get_dress_detail_data(id).collect {
                _dress_detail_data.postValue(it)
            }
        }
    }

     fun set_dress_like_data(updateDressLikeDto: UpdateDressLikeDto) {
        viewModelScope.launch {
            dressRepository.set_dress_like_data(updateDressLikeDto).collect {
                _update_dress_like_data.postValue(it)
            }
        }
    }

     fun get_dress_like_data() {
        viewModelScope.launch {
            dressRepository.get_dress_like_data().collect {
                _dress_like_data.postValue(it)
            }
        }
    }

     fun get_dress_order_detail_data(dress_reservation_id: Int)  {
        viewModelScope.launch {
            dressRepository.get_dress_order_detail_data(dress_reservation_id).collect {
                _dress_order_detail_data.postValue(it)
            }
        }
    }

     fun get_dress_order_data(status: String) {
        viewModelScope.launch {
            dressRepository.get_dress_order_data(status).collect {
                _dress_order_data.postValue(it)
            }
        }
    }

     fun get_dress_search_data(category: String,lat: Double,lng: Double,name: String,sort: String) {
        viewModelScope.launch {
            dressRepository.get_dress_search_data(category,lat,lng,name,sort).collect {
                _dress_search_data.postValue(it)
            }
        }
    }

    fun set_dress_reservation(dressReservationDto: DressReservationDto) {
        viewModelScope.launch {
            dressRepository.set_dress_reservation(dressReservationDto).collect {
                _update_dress_reservation_data.postValue(it)
            }
        }
    }

    fun get_dress_reservation_store_data(id: Int) {
        viewModelScope.launch {
            dressRepository.get_dress_reservation_store_data(id).collect {
                _dress_reservation_store_data.postValue(it)
            }
        }
    }

    fun set_dress_reservation_cancel(reservation_id: Int) {
        viewModelScope.launch {
            dressRepository.set_dress_reservation_cancel(reservation_id).collect {
                _dress_reservation_cancel.postValue(it)
            }
        }
    }

//    suspend fun get_dress_detail_data(id: Int) {
//        DressService.dressService.get_dress_detail_data(id).enqueue(object : Callback<DressDetailDto> {
//                override fun onResponse(
//                    call: Call<DressDetailDto>,
//                    response: Response<DressDetailDto>
//                ) {
//                    if (response.isSuccessful) {
//                        _dress_detail_data.value = (response.body())
//                    } else {
//                        _dress_detail_data.postValue(null)
//                        Log.d("whatisthis", "_dress_detail_data, response 못받음")
//                    }
//                }
//
//                override fun onFailure(call: Call<DressDetailDto>, t: Throwable) {
//                    Log.d(
//                        "whatisthis",
//                        "get_dress_detail_data, 네트워크 오류가 발생했습니다." + t.message.toString()
//                    )
//                }
//            })
//    }
//
//    suspend fun set_dress_like_data(updateDressLikeDto: UpdateDressLikeDto) {
//        DressService.dressService.set_dress_like_data(updateDressLikeDto).enqueue(object :
//            Callback<UpdateDressLikeDto> {
//            override fun onResponse(
//                call: Call<UpdateDressLikeDto>,
//                response: Response<UpdateDressLikeDto>
//            ) {
//                if (response.isSuccessful) {
//                    _update_dress_like_data.value = response.body()
//                } else {
//                    Log.d("whatisthis", "좋아요 실패")
//                }
//            }
//
//            override fun onFailure(call: Call<UpdateDressLikeDto>, t: Throwable) {
//                Log.d("whatisthis", "set_dress_like_data, 네트워크 오류가 발생했습니다." + t.message.toString())
//            }
//        })
//    }
//
//    suspend fun get_dress_like_data() {
//        DressService.dressService.get_dress_like_data()
//            .enqueue(object : Callback<List<DressLikeDto>> {
//                override fun onResponse(
//                    call: Call<List<DressLikeDto>>,
//                    response: Response<List<DressLikeDto>>
//                ) {
//                    if (response.isSuccessful) {
//                        _dress_like_data.postValue(response.body())
//                    } else {
//                        _dress_like_data.postValue(null)
//                        Log.d("whatisthis", "_dress_like_data, response 못받음")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<DressLikeDto>>, t: Throwable) {
//                    Log.d(
//                        "whatisthis",
//                        "get_dress_like_data, 네트워크 오류가 발생했습니다." + t.message.toString()
//                    )
//                }
//            })
//    }
//
////    @GET("dresses/orders/{dress_reservation_id}")
////    fun get_dress_reservation_dress_data(
////        @Path("dress_reservation_id")dress_reservation_id:Int
////    ):Call<DressOrderDto>
//
//    //의상 주문 상세 내역 조회
//    suspend fun get_dress_reservation_dress_data(dress_reservation_id: Int) {
//        DressService.dressService.get_dress_reservation_dress_data(dress_reservation_id)
//            .enqueue(object :
//                Callback<List<DressOrderDto>> {
//                override fun onResponse(
//                    call: Call<List<DressOrderDto>>,
//                    response: Response<List<DressOrderDto>>
//                ) {
//                    if (response.isSuccessful) {
//                        _dress_reservation_dress_data.value = response.body()
//                    } else {
//                        Log.d("whatisthis", " 실패")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<DressOrderDto>>, t: Throwable) {
//                    Log.d(
//                        "whatisthis",
//                        "get_dress_reservation_dress_data, 네트워크 오류가 발생했습니다." + t.message.toString()
//                    )
//                }
//            })
//    }//
//
//    suspend fun get_dress_resevation_data(status: String) {
//        DressService.dressService.get_dress_resevation_data(status)
//            .enqueue(object :
//                Callback<List<DressOrderListDto>> {
//                override fun onResponse(
//                    call: Call<List<DressOrderListDto>>,
//                    response: Response<List<DressOrderListDto>>
//                ) {
//                    if (response.isSuccessful) {
//                        _dress_resevation_data.value = response.body()
//                    } else {
//                        Log.d("whatisthis", " 실패")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<DressOrderListDto>>, t: Throwable) {
//                    Log.d(
//                        "whatisthis",
//                        "get_dress_reservation_data, 네트워크 오류가 발생했습니다." + t.message.toString()
//                    )
//                }
//            })
//    }
//
//    suspend fun get_dress_search_data(category: String,lat: Double,lng: Double,name: String,sort: String) {
//        DressService.dressService.get_dress_search_data(category,lat,lng,name,sort).enqueue(object : Callback<DressSearchDto> {
//            override fun onResponse(
//                call: Call<DressSearchDto>,
//                response: Response<DressSearchDto>
//            ) {
//                if (response.isSuccessful) {
//                    _dress_search_data.postValue(response.body())
//                } else {
//                    _dress_search_data.postValue(null)
//                    Log.d("whatisthis","_dress_search_data, response 못받음")
//                }
//            }
//
//            override fun onFailure(call: Call<DressSearchDto>, t: Throwable) {
//                Log.d("whatisthis", "get_dress_search_data, 네트워크 오류가 발생했습니다." + t.message.toString())
//            }
//        })
//    }

}