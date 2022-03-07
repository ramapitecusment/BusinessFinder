package com.example.businessfinder.scenes.chat.chatchannels

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.databinding.FragmentChatChannelsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatChannelsFragment :
    BaseFragment<ChatChannelsViewModel, FragmentChatChannelsBinding>(R.layout.fragment_chat_channels) {
    override val viewModel: ChatChannelsViewModel by viewModel()
    override val binding: FragmentChatChannelsBinding by viewBinding(FragmentChatChannelsBinding::bind)

    private val onChatChannelClick: (userUID: String) -> Unit = { Navigator.goToChatScreen(this, it) }
    private val adapter = ChatChannelRecyclerViewAdapter(onChatChannelClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.usersRecyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        bindRecyclerViewAdapter(viewModel.usersList, adapter)
    }


}