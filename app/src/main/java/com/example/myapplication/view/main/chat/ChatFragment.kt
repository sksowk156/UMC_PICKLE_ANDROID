package com.example.myapplication.view.main.chat

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.base.BaseFragment


class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    override fun init() {
        initAppbar(binding.chatToolbar,"채팅",false,true)
    }
}