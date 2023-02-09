package com.example.myapplication.db.remote.model

data class HomeModel(
    var newDresses: List<NewDresse?>? = null,
    var recDresses: List<RecDresse?>? = null,
    var recentView: List<RecentView?>? = null
)

data class NewDresse(
    var dress_default_img: String? = null,
    var dress_id: Int,
    var dress_name: String? = null,
    var dress_price: String? = null,
    var store_id: Int,
    var store_name: String? = null
)

data class RecDresse(
    var dress_default_img: String? = null,
    var dress_id: Int,
    var dress_name: String? = null,
    var dress_price: String? = null,
    var store_id: Int,
    var store_name: String? = null
)

data class RecentView(
    var dress_default_img: String? = null,
    var dress_id: Int,
    var dress_name: String? = null,
    var dress_price: String? = null,
    var store_id: Int,
    var store_name: String? = null
)