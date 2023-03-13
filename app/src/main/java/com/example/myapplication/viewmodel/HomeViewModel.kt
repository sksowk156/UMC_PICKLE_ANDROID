package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.DressHomeDto
import com.example.myapplication.data.remote.model.DressOverviewDto
import com.example.myapplication.repository.HomeRepository
import com.example.myapplication.widget.utils.Event
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _recent_bt_event = MutableLiveData<Event<Boolean>>()
    val recent_bt_event : LiveData<Event<Boolean>>
        get() = _recent_bt_event

    private val _new_bt_event = MutableLiveData<Event<Boolean>>()
    val new_bt_event : LiveData<Event<Boolean>>
        get() = _new_bt_event

    fun onRecentBTEvent() {
        _recent_bt_event.value = Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onNewBTEvent() {
        _new_bt_event.value = Event(true)  // Trigger the event by setting a new Event as a new value
    }

    private var _home_latlng = MutableLiveData<Pair<Double, Double>>()
    val home_latlng: LiveData<Pair<Double, Double>> get() = _home_latlng

    private var _home_data = MutableLiveData<NetworkResult<DressHomeDto>>()
    val home_data: LiveData<NetworkResult<DressHomeDto>> get() = _home_data

    private var _home_new_data = MutableLiveData<NetworkResult<List<DressOverviewDto>>>()
    val home_new_data: LiveData<NetworkResult<List<DressOverviewDto>>> get() = _home_new_data

    private var _home_recent_data = MutableLiveData<NetworkResult<List<DressOverviewDto>>>()
    val home_recent_data: LiveData<NetworkResult<List<DressOverviewDto>>> get() = _home_recent_data

    private var _home_recommendation_data = MutableLiveData<NetworkResult<List<DressOverviewDto>>>()
    val home_recommendation_data: LiveData<NetworkResult<List<DressOverviewDto>>> get() = _home_recommendation_data

    init {
        _home_latlng.value = Pair(37.5581, 126.9260)
    }

    fun set_home_latlng(lat_lng:Pair<Double, Double>) {
        _home_latlng.value = lat_lng
    }


    fun get_home_data(lat: Double, lng: Double) {
        viewModelScope.launch {
            homeRepository.get_home_data(lat, lng).collect {
                _home_data.value = it
            }
        }
    }

    fun get_home_new_data(lat: Double, lng: Double) {
        viewModelScope.launch {
            homeRepository.get_home_new_data(lat, lng).collect {
                _home_new_data.postValue(it)
            }
        }
    }

    fun get_home_recent_data() {
        viewModelScope.launch {
            homeRepository.get_home_recent_data().collect {
                _home_recent_data.postValue(it)
            }
        }
    }

}