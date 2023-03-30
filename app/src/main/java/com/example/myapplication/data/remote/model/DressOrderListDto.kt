package com.example.myapplication.data.remote.model

data class DressOrderListDto(
    var dress_reservation_id: Int,
    var order_time: String,
    var store_name: String,
    var dress_name: String,
    var dress_image_url: String,
    var price: String,
    var reservation_status: String
)
