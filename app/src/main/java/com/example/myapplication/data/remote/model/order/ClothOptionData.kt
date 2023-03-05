package com.example.myapplication.data.remote.model.order

import com.example.myapplication.data.remote.model.DressOptionDto

data class ClothOptionData(
    var clothPrice : Int,
    var dress_option1: DressOptionDto? = DressOptionDto(),
    var dress_option2: DressOptionDto? = DressOptionDto()
)
