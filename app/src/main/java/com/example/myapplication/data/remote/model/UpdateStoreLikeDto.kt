package com.example.myapplication.data.remote.model

data class UpdateStoreLikeDto(
    var is_like: Boolean?=false,
    var store_id: Int
    )