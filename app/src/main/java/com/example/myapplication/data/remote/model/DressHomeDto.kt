package com.example.myapplication.data.remote.model

data class DressHomeDto(
    var newDresses: List<DressOverviewDto>? = null,
    var recDresses: List<DressOverviewDto>? = null,
    var recentView: List<DressOverviewDto>? = null
)