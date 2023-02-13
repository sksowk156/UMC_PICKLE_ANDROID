package com.example.myapplication.db.remote.model

data class StoreLikeDto(
    var address: String,
    var close_time: LocalTime?=null,
    var imageUrl: String?=null,
    var name: String,
    var open_time: LocalTime?=null,
    var store_id: Int,
    var store_open_day: String?=null
)
