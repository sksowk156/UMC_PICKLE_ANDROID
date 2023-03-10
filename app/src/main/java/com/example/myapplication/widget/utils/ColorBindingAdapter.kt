package com.example.myapplication.widget.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.myapplication.R

object ColorBindingAdapter {
    @JvmStatic
    @BindingAdapter("searchOptionColor")
    fun setTextColor(view: TextView, category: String) {
        if (view.text.toString() == category) {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.selected_searchoption_text))
        } else {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.unselected_searchoption_text))
        }
    }

}