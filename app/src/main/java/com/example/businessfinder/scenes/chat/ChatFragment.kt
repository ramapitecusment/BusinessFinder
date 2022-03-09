package com.example.businessfinder.scenes.chat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.common.extensions.bindTitle
import com.example.businessfinder.databinding.FragmentChatBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>(R.layout.fragment_chat) {
    override val viewModel: ChatViewModel by viewModel()
    override val binding: FragmentChatBinding by viewBinding(FragmentChatBinding::bind)
    private val args: ChatFragmentArgs by navArgs()
    private val adapter = ChatRecyclerViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.otherUserUID)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.chatRecyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        bindTitle(viewModel.title)
        bindRecyclerViewAdapter(viewModel.messages, adapter)
    }
}