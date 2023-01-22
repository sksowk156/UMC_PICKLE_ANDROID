package com.example.myapplication.ui.main.location

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.ui.main.BaseFragment


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {

    override fun init() {
        binding.ex2.setOnClickListener {
            parentFragmentManager.popBackStackImmediate(null,0)
        }
        initAppbar()
    }

    private fun initAppbar(){

    }
}