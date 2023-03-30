package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.repository.StoreRepository
import com.example.myapplication.widget.utils.Event
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    ViewModel() {
    private val _floatingmap_bt_event = MutableLiveData<Event<Boolean>>()
    val floatingmap_bt_event: LiveData<Event<Boolean>>
        get() = _floatingmap_bt_event

    private val _floatingaround_bt_event = MutableLiveData<Event<Boolean>>()
    val floatingaround_bt_event: LiveData<Event<Boolean>>
        get() = _floatingaround_bt_event


    private val _store_bt_event = MutableLiveData<Event<Boolean>>()
    val store_bt_event: LiveData<Event<Boolean>>
        get() = _store_bt_event

    fun onFloatingMapBTEvent() {
        _floatingmap_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onFloatingAroundBTEvent() {
        _floatingaround_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onStoreBTEvent() {
        _store_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    private var _screen_latlng = MutableLiveData<Pair<Double, Double>>()
    val screen_latlng: LiveData<Pair<Double, Double>> get() = _screen_latlng

    private var _store_near_data = MutableLiveData<NetworkResult<StoreCoordDtoList>>()
    val store_near_data: LiveData<NetworkResult<StoreCoordDtoList>> get() = _store_near_data

    private var _store_detail_data = MutableLiveData<NetworkResult<StoreDetailDto>>()
    val store_detail_data: LiveData<NetworkResult<StoreDetailDto>> get() = _store_detail_data

    private var _store_like_data = MutableLiveData<NetworkResult<List<StoreLikeDto>>>()
    val store_like_data: LiveData<NetworkResult<List<StoreLikeDto>>> get() = _store_like_data

    private var _update_store_like_data = MutableLiveData<NetworkResult<UpdateStoreLikeDto>>()
    val update_store_like_data: LiveData<NetworkResult<UpdateStoreLikeDto>> get() = _update_store_like_data

    init {
        _screen_latlng.value = Pair(0.0, 0.0)
    }

    fun set_screen_latlng(lat: Double, lng: Double) {
        _screen_latlng.value = Pair(lat, lng)
    }

    fun get_store_like_data() {
        viewModelScope.launch {
            storeRepository.get_store_like_data().collect {
                _store_like_data.postValue(it)
            }
        }
    }

    fun get_store_near_data(lat: Double, lng: Double) {
        viewModelScope.launch {
            storeRepository.get_store_near_data(lat, lng).collect {
                _store_near_data.value = (it)
            }
        }
    }

    fun get_store_detail_data(id: Int, category: String) {
        viewModelScope.launch {
            storeRepository.get_store_detail_data(id, category).collect {
                _store_detail_data.postValue(it)
            }
        }
    }

    fun set_store_like_data(updateStoreLikeDto: UpdateStoreLikeDto) {
        viewModelScope.launch {
            storeRepository.set_store_like_data(updateStoreLikeDto).collect {
                _update_store_like_data.value = (it)
            }
        }
    }

}