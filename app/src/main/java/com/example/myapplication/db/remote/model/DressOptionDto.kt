package com.example.myapplication.db.remote.model

data class DressOptionDto(
    var dress_option_detail_list: List<DressOptionDetailDto> ?= listOf(),
    var dress_option_name: String? = null
)