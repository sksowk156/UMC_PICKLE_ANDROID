package com.example.myapplication.ui.main

import android.view.View

interface ItemClickInterface {
    fun onItemImageClick(view: View, position: Int)
    fun onItemMarketNameClick(view: View, position: Int)
    fun onItemButtonClick(view: View, position: Int)
    fun onItemFavoriteClick(view: View, position: Int)
}