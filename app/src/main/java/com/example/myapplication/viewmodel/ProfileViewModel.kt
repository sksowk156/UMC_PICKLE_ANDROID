package com.example.myapplication.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel:ViewModel() {
    private var _profile_photo = MutableLiveData<Uri>()
    val profile_photo: LiveData<Uri> get() = _profile_photo

    private var _default_profile_photo=MutableLiveData<Int>()
    val default_profile_photo:LiveData<Int>get() = _default_profile_photo

    fun set_profile_photo(photo:Uri){
        _profile_photo.value = photo
    }

    fun set_default_photo(def:Int){
        _default_profile_photo.value=def
    }
}