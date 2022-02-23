package com.example.businessfinder.scenes.chat

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.databinding.FragmentChatBinding

class ChatFragment : BaseFragment<ChatViewModel>(R.layout.fragment_chat) {
    override val viewModel: ChatViewModel by viewModels()
    private val binding: FragmentChatBinding by viewBinding(FragmentChatBinding::bind)
}