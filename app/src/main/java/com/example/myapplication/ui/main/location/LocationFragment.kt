package com.example.myapplication.ui.main.location

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.location.map.MapFragment
import com.example.myapplication.viewmodel.MapViewModel


class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {

    override fun init() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.location_layout, MapFragment())
            .commitAllowingStateLoss()
        initAppbar(binding.locationToolbarcontent,binding.locationToolbar,"주변매장",false,true)
    }
}