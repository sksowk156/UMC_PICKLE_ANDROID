package com.example.myapplication.db.remote.model

data class DressOrderDto(
    val comment: String,
    val dress_image_url: String,
    val dress_name: String,
    val dress_option1_name: String,
    val dress_option2_name: String,
    val hours_of_operation: String,
    val pickup_datetime: String,
    val price: String,
    val reservation_status: String,
    val reserved_dress_id: Int,
    val store_address: String,
    val store_name: String,
    val store_open_day: String
)