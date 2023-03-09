package com.example.myapplication.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.order.ClothOptionData
import com.example.myapplication.data.remote.model.order.ClothOrderData
import com.example.myapplication.widget.config.Event

class OrderViewModel : ViewModel() {

    private val _pickup_bt_event = MutableLiveData<Event<Boolean>>()
    val pickup_bt_event: LiveData<Event<Boolean>>
        get() = _pickup_bt_event

    private val _pickupdate_bt_event = MutableLiveData<Event<Boolean>>()
    val pickupdate_bt_event: LiveData<Event<Boolean>>
        get() = _pickupdate_bt_event

    private val _pickupreservation_bt_event = MutableLiveData<Event<Boolean>>()
    val pickupreservation_bt_event: LiveData<Event<Boolean>>
        get() = _pickupreservation_bt_event

    private val _pickuptime_bt_event = MutableLiveData<Event<View>>()
    val pickuptime_bt_event: LiveData<Event<View>>
        get() = _pickuptime_bt_event

    fun onPickupBTEvent() {
        _pickup_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onPickupDateBTEvent() {
        _pickupdate_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onPickupReservationBTEvent() {
        _pickupreservation_bt_event.value =
            Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onPickupTimeBTEvent(time: View) {
        _pickuptime_bt_event.value =
            Event(time)  // Trigger the event by setting a new Event as a new value
    }

    var _order_request_data = MutableLiveData<String>()

    private var _order_data = MutableLiveData<ArrayList<ClothOrderData>>()
    val order_data: LiveData<ArrayList<ClothOrderData>> get() = _order_data

    private var _calculate_order_price = MutableLiveData<Int>()
    val calculate_order_price: LiveData<Int> get() = _calculate_order_price

    private var _option_data = MutableLiveData<ClothOptionData>()
    val option_data: LiveData<ClothOptionData> get() = _option_data

    private var _pickuptime_data = MutableLiveData<TextView>()
    val pickuptime_data: LiveData<TextView> get() = _pickuptime_data


    fun set_order_data(clothorderdatalist: ArrayList<ClothOrderData>?) {
        _order_data.value = clothorderdatalist
    }

    fun set_pickuptime_data(time: TextView?) {
        _pickuptime_data.value = time
    }

    fun get_calculate_order_price() {

        var total = 0
        for (i in 0.._order_data.value!!.size - 1) {
            total += order_data.value!!.get(i).clothPrice * order_data.value!!.get(i).count
        }
        _calculate_order_price.value = total

    }

    fun set_option_data(clothOptionData: ClothOptionData) {
        _option_data.value = clothOptionData
    }
}