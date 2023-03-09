package com.example.myapplication.view.main.profile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBlankBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.widget.utils.NetworkResult

class ProfileBlankFragment :
    BaseFragment<FragmentProfileBlankBinding>(R.layout.fragment_profile_blank) {

    override fun savedatainit() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.profileblank_layout, ProfileFragment(), "profile")
            .commitAllowingStateLoss()
    }

    override fun init() {}
}