package com.example.myapplication.data.remote.model

class StoreCoordDtoList : ArrayList<StoreCoordDto>()

data class StoreCoordDto(
    var address: String? = null,
    var hoursOfOperation: String? = null,
    var latitude: Double,
    var longitude: Double,
    var open_day: String? = null,
    var store_id: Int,
    var store_img: String? = null,
    var store_like: Boolean = false,
    var store_name: String
)