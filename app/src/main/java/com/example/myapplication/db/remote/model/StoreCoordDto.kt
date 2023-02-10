package com.example.myapplication.db.remote.model

class StoreCoordDtoList : ArrayList<StoreCoordDto>()

data class StoreCoordDto(
    var dist: Double,
    var id: Int,
    var latitude: Double,
    var longitude: Double,
    var name: String
)