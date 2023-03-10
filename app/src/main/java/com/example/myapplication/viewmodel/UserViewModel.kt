package com.example.myapplication.viewmodel

import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.ResultOfSetDto
import com.example.myapplication.data.remote.model.UserProfileDto
import com.example.myapplication.data.remote.model.UserProfileEditDto
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.widget.utils.Event
import com.example.myapplication.widget.utils.NetworkResult
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _update_bt_event = MutableLiveData<Event<View>>()
    val update_bt_event: LiveData<Event<View>>
        get() = _update_bt_event

    fun onUpdateBTEvent(view:View) {
        _update_bt_event.value =
            Event(view)  // Trigger the event by setting a new Event as a new value
    }

    private val _fix_bt_event = MutableLiveData<Event<Boolean>>()
    val fix_bt_event: LiveData<Event<Boolean>>
        get() = _fix_bt_event

    fun onFixBTEvent() {
        _fix_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    private val _save_bt_event = MutableLiveData<Event<Boolean>>()
    val save_bt_event: LiveData<Event<Boolean>>
        get() = _save_bt_event

    fun onSaveBTEvent() {
        _save_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    private var _user_profile_data = MutableLiveData<NetworkResult<UserProfileDto>>()
    val user_profile_data: LiveData<NetworkResult<UserProfileDto>> get() = _user_profile_data

    private var _user_home_name_data = MutableLiveData<String>()
    val user_home_name_data: LiveData<String> get() = _user_home_name_data

    private var _user_profile_edit_result_data = MutableLiveData<NetworkResult<ResultOfSetDto>>()
    val user_profile_edit_result_data: LiveData<NetworkResult<ResultOfSetDto>> get() = _user_profile_edit_result_data

    private var _profile_photo = MutableLiveData<Uri>()
    val profile_photo: LiveData<Uri> get() = _profile_photo

    private var _default_profile_photo = MutableLiveData<Int>()
    val default_profile_photo: LiveData<Int> get() = _default_profile_photo

    var user_name_data = MutableLiveData<String>()
    var user_email_data = MutableLiveData<String>()
    var user_image_data = MutableLiveData<String>()
    var user_data_change = MutableLiveData<Boolean>()
    var user_data_change_button = MutableLiveData<Boolean>()

    init{
        user_data_change.value = false
        user_data_change_button.value = false
    }

    fun set_profile_photo(photo: Uri) {
        _profile_photo.value = photo
    }

    fun set_default_photo(def: Int) {
        _default_profile_photo.value = def
    }

    fun set_home_user_name_data(name: String) {
        _user_home_name_data.value = name
    }

    fun get_user_profile_data() {
        viewModelScope.launch {
            userRepository.get_user_profile_data().collect {
                _user_profile_data.value = it
            }
        }
    }

    fun set_user_profile_data(userUpdateDtoDto: UserProfileEditDto) {
        viewModelScope.launch {
            userRepository.set_user_profile_data(userUpdateDtoDto).collect {
                _user_profile_edit_result_data.value = it
            }
        }
    }
}