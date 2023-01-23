package com.example.myapplication.ui.main.profile.myprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMyprofileBinding
import com.example.myapplication.ui.main.BaseFragment

class MyprofileFragment : BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {
    override fun init() {
        hideBottomNavigation(true)
    }

}