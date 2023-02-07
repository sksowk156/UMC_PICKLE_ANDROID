package com.example.myapplication.db.remote.remotedata

data class PostModel( // 카카오에 접근할 때 필요한 토큰
    var accessToken : String
)

data class PostResult( // 카카오에서 발급받는 앱 토큰과 boolean값
    var appToken : String,
    var isNewMember : Boolean
)
