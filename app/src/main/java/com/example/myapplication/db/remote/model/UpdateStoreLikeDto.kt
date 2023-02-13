package com.example.myapplication.db.remote.model

data class UpdateStoreLikeDto(
    var store_id: Int,
    var is_like: Boolean?=false
)