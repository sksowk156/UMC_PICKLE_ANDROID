package com.example.myapplication.db.remote.model

class MapModel : ArrayList<MapModelItem>()

data class MapModelItem(
    var dist: Double,
    var id: Int,
    var latitude: Double,
    var longitude: Double,
    var name: String
)

data class StoreDetailData(
    var hours_of_operation: String? = null,
    var storeId: Int? = null,
    var store_address: String? = null,
    var store_dress_list: List<Any?>? = null,
    var store_image_url: String? = null,
    var store_name: String? = null,
    var store_open_day: String? = null
)

data class StoreFavoriteModel(
    var address: String,
    var close_time: CloseTime,
    var imageUrl: String,
    var name: String,
    var open_time: OpenTime,
    var store_id: Int,
    var store_open_day: String
)

data class CloseTime(
    var hour: Int? = null,
    var minute: Int? = null,
    var nano: Int? = null,
    var second: Int? = null
)

data class OpenTime(
    var hour: Int? = null,
    var minute: Int? = null,
    var nano: Int? = null,
    var second: Int? = null
)