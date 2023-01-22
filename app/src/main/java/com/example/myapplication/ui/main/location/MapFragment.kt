package com.example.myapplication.ui.main.location

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.ui.main.BaseFragment
import kotlinx.coroutines.NonDisposableHandle.parent

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    override fun init() {
        binding.ex.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.map_layout, AroundFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

}