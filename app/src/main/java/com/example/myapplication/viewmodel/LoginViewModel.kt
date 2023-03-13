package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.remotedata.AuthRequest
import com.example.myapplication.data.remote.remotedata.AuthResponse
import com.example.myapplication.repository.LoginRepository
import com.example.myapplication.widget.utils.Event
import com.example.myapplication.widget.utils.NetworkResult
import com.example.myapplication.widget.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesmanager: SharedPreferencesManager
) : ViewModel() {
    private val _kakao_bt_event = MutableLiveData<Event<Boolean>>()
    val kakao_bt_event: LiveData<Event<Boolean>>
        get() = _kakao_bt_event

    fun onKakaoBTEvent() {
        _kakao_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    private var _jwt_data = MutableLiveData<NetworkResult<AuthResponse>>()
    val jwt_data: LiveData<NetworkResult<AuthResponse>> get() = _jwt_data

    fun create(jsonparams: AuthRequest) {
        viewModelScope.launch {
            loginRepository.create(jsonparams).collect {
                _jwt_data.value = it
            }
        }
    }

    fun setJwt(key: String, value: String) {
        sharedPreferencesmanager.setJwt(key, value)
    }

    fun deleteJwt() = sharedPreferencesmanager.deleteJwt()
}