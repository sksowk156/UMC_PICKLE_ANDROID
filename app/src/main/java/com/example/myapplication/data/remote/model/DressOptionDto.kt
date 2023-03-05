package com.example.myapplication.data.remote.model

data class DressOptionDto(
    var dress_option_detail_list: List<DressOptionDetailDto> ?= listOf(),
    var dress_option_name: String? = null
)