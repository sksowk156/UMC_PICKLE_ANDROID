package com.example.myapplication.ui


data class PostModel(
    var accessToken : String
)


data class PostResult(
    var appToken : String,
    var isNewMember : Boolean
)
