package com.example.myapplication.data.remote.model

data class DressReservationDto (
    var comment: String = "",
    var dress_id: Int,
    var pickup_datetime: String = "",
    var price: Int,
    var reserved_dress_list: List<StockQuantityDto>? = listOf(),
    var store_id: Int
)