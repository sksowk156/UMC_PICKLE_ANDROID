package com.example.myapplication.db.remote.model

data class DressLikeDto(
    val dress_id: Int,
    val image: String?=null,
    val name: String?=null,
    val price: String?=null
)