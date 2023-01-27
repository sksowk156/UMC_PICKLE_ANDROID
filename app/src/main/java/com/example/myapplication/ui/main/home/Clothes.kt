package com.example.myapplication.ui.main.home



var clothesList= mutableListOf<Clothes>()
var newclothesList= mutableListOf<Clothes>()

var CLOTHES_ID_EXTRA="clothesExtra"

data class Clothes(
    var image: Int,
    var store:String,
    var name:String,
    var price:Int,
    val id:Int?= newclothesList.size

)
