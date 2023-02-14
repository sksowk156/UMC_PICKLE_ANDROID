package com.example.myapplication.db.remote.model

data class DressOrderListDto(
    var dress_image_url: String,
    var dress_name: String,
    var pickup_datetime: String,
    var price: String,
    var reservation_status: String,
    var reserved_dress_id: Int,
    var store_name: String
)
