package com.example.businessfinder.scenes.chat.chatchannels

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.databinding.FragmentChatChannelsBinding

class ChatChannelsFragment : BaseFragment<ChatChannelsViewModel, FragmentChatChannelsBinding>(R.layout.fragment_chat_channels) {
    override val viewModel: ChatChannelsViewModel by viewModels()
    override val binding: FragmentChatChannelsBinding by viewBinding(FragmentChatChannelsBinding::bind)
}