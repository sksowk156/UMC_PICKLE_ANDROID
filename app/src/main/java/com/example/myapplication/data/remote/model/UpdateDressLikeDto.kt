package com.example.myapplication.data.remote.model

data class UpdateDressLikeDto(
    var dress_id: Int,
    var is_like: Boolean?=false
)