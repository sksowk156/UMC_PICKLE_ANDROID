package com.example.myapplication.viewmodel

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.widget.config.Event

class OptionViewModel : ViewModel() {

    private val _category_bt_event = MutableLiveData<Event<View>>()
    val category_bt_event : LiveData<Event<View>>
        get() = _category_bt_event

    private val _sort_bt_event = MutableLiveData<Event<View>>()
    val sort_bt_event : LiveData<Event<View>>
        get() = _sort_bt_event

    private val _clothkind_bt_event = MutableLiveData<Event<View>>()
    val clothkind_bt_event : LiveData<Event<View>>
        get() = _clothkind_bt_event

    fun onCategoryBTEvent(category: View) {
        _category_bt_event.value = Event(category)  // Trigger the event by setting a new Event as a new value
    }

    fun onSortBTEvent(sort: View) {
        _sort_bt_event.value = Event(sort)  // Trigger the event by setting a new Event as a new value
    }

    fun onClothKindBTEvent(clothkind: View) {
        _clothkind_bt_event.value = Event(clothkind)  // Trigger the event by setting a new Event as a new value
    }


    private var _category_data = MutableLiveData<String>()
    val category_data: LiveData<String> get() = _category_data

    private var _sort_data = MutableLiveData<String>()
    val sort_data: LiveData<String> get() = _sort_data

    private var _clothkind_data = MutableLiveData<TextView>()
    val clothkind_data: LiveData<TextView> get() = _clothkind_data

    init {
        _category_data.value = "전체"
        _sort_data.value = "좋아요많은순"
    }

    fun set_category_data(category: String) {
        _category_data.value = category
    }

    fun set_sort_data(sort: String) {
        _sort_data.value = sort
    }

    fun set_clothkind_data(clothkind: TextView) {
        _clothkind_data.value = clothkind
    }
}