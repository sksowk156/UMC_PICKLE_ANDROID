package com.example.myapplication.ui.main.chat

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.base.AppbarData
import com.example.myapplication.ui.base.BaseFragment


class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {
    lateinit var appbarData: AppbarData

    override fun init() {
        changeAppbar()
    }
    fun changeAppbar(){
        initAppbar("채팅", false)
    }
}