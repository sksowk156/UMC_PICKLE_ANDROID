package com.example.myapplication.ui.main

import android.view.View

interface ItemClickInterface {
    fun onItemImageClick(id:Int, position: Int)
    fun onItemStoreNameClick(id:Int, position: Int)
    fun onItemButtonClick(id:Int, position: Int)
    fun onItemFavoriteClick(id:Int, position: Int)
}