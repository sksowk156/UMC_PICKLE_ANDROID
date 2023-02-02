package com.example.myapplication.db.remote.remotedata

data class LoginTokenAccessData(
    var email: String? = null,
    var name: String? = null
)

data class LoginTokenResultData(
    var token: String? = null
)