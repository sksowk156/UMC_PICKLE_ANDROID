package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.LoginRepository
import com.example.myapplication.repository.StoreRepository

class StoreViewModelFactory (
    private val storeRepository: StoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(StoreRepository::class.java).newInstance(storeRepository)
    }
}