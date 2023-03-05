package com.example.myapplication.data.remote.model.search

data class SearchOptionData(
    var category : String ?= "전체",
    var sort: String ?= "좋아요 많은 순",
)
