package com.example.myapplication.view

import android.view.View

interface ItemCardClickInterface {
    fun onItemClothImageClick(id:Int, position: Int)
    fun onItemStoreNameClick(id:Int, position: Int)
    fun onItemClothFavoriteClick(like:Boolean, id:Int, view : View, position: Int)
}