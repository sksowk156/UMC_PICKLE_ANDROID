package com.example.myapplication.data.remote.model

data class UserProfileEditDto(
    var email: String,
    var image: String?=null,
    var name: String
)