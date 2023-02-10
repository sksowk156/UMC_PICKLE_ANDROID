package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.store.clothdetail.ClothOrderData

class OrderViewModel : ViewModel() {

    private var _order_data = MutableLiveData<ArrayList<ClothOrderData>>()
    val order_data: LiveData<ArrayList<ClothOrderData>> get() = _order_data

    fun set_order_data(clothorderdatalist:ArrayList<ClothOrderData>) {
        _order_data.value = clothorderdatalist
    }
}