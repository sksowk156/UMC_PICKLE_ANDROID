package com.example.myapplication.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.widget.utils.Event

class OptionViewModel : ViewModel() {

    private val _history_bt_event = MutableLiveData<Event<Boolean>>()
    val history_bt_event : LiveData<Event<Boolean>>
        get() = _history_bt_event

    private val _category_bt_event = MutableLiveData<Event<View>>()
    val category_bt_event : LiveData<Event<View>>
        get() = _category_bt_event

    private val _sort_bt_event = MutableLiveData<Event<View>>()
    val sort_bt_event : LiveData<Event<View>>
        get() = _sort_bt_event

    private val _clothkind_bt_event = MutableLiveData<Event<View>>()
    val clothkind_bt_event : LiveData<Event<View>>
        get() = _clothkind_bt_event

    fun onHistoryBTEvent() {
        _history_bt_event.value = Event(true)  // Trigger the event by setting a new Event as a new value
    }

    fun onCategoryBTEvent(category: View) {
        _category_bt_event.value = Event(category)  // Trigger the event by setting a new Event as a new value
    }

    fun onSortBTEvent(sort: View) {
        _sort_bt_event.value = Event(sort)  // Trigger the event by setting a new Event as a new value
    }

    fun onClothKindBTEvent(clothkind: View) {
        _clothkind_bt_event.value = Event(clothkind)  // Trigger the event by setting a new Event as a new value
    }

    var _searchword_data = MutableLiveData<String>()
    val searchword_data: LiveData<String> get() = _searchword_data

    private var _latlng_data = MutableLiveData<Pair<Double,Double>>()
    val latlng_data: LiveData<Pair<Double,Double>> get() = _latlng_data

    private var _category_data = MutableLiveData<String>()
    val category_data: LiveData<String> get() = _category_data

    private var _sort_data = MutableLiveData<String>()
    val sort_data: LiveData<String> get() = _sort_data

    private var _clothkind_data = MutableLiveData<TextView>()
    val clothkind_data: LiveData<TextView> get() = _clothkind_data

    init {
        initOptiondata()
    }

    fun initOptiondata(){
        _category_data.value = "전체"
        _sort_data.value = "좋아요많은순"
    }

    fun set_searchword_data(searchword: String) {
        _searchword_data.value = searchword
    }

    fun set_latlng_data(latlng: Pair<Double,Double>) {
        _latlng_data.value = latlng
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