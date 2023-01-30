package com.example.myapplication.ui.main.chat

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.base.BaseFragment


class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    override fun init() {
        initAppbar(binding.chatTtoolbarcontent,binding.chatToolbar,"채팅")
    }
}