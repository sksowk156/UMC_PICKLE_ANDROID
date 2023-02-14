package com.example.myapplication.ui.main.profile

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBlankBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewmodel.UserViewModel

class ProfileBlankFragment :
    BaseFragment<FragmentProfileBlankBinding>(R.layout.fragment_profile_blank) {
    private lateinit var userViewModel: UserViewModel

    override fun savedatainit() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.profileblank_layout, ProfileFragment(), "profile")
            .commitAllowingStateLoss()
    }

    override fun init() {
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.get_user_profile_data()
    }

}