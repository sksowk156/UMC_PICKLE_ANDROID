package com.example.myapplication.ui.main.profile.orderstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderstatusBinding
import com.example.myapplication.ui.main.BaseFragment

class OrderstatusFragment : BaseFragment<FragmentOrderstatusBinding>(R.layout.fragment_orderstatus) {
    override fun init() {
        hideBottomNavigation(true)
    }

}