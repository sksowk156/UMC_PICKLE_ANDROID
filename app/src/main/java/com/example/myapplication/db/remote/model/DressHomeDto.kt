package com.example.myapplication.db.remote.model

data class DressHomeDto(
    var newDresses: List<DressOverviewDto>? = null,
    var recDresses: List<DressOverviewDto>? = null,
    var recentView: List<DressOverviewDto>? = null
)