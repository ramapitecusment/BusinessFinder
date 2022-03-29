package com.example.businessfinder.scenes.chat

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.common.extensions.bindTextTwoWay
import com.example.businessfinder.common.extensions.bindTitle
import com.example.businessfinder.databinding.FragmentChatBinding
import com.example.businessfinder.services.FirebaseServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>(R.layout.fragment_chat) {
    override val viewModel: ChatViewModel by viewModel()
    override val binding: FragmentChatBinding by viewBinding(FragmentChatBinding::bind)
    private val args: ChatFragmentArgs by navArgs()

    private val adapter = ChatRecyclerViewAdapter(FirebaseServices.auth.currentUser!!.uid)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.otherUserUID)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        with(binding) {
            chatRecyclerView.adapter = adapter
            sendButton.setOnClickListener { viewModel.onSendMessageClicked() }
            sendImageButton.setOnClickListener { }
        }
    }

    private fun bindViewModel() {
        bindTitle(viewModel.title)
        bindTextTwoWay(viewModel.messageToSend, binding.messageToSend)
        bindRecyclerViewAdapter(viewModel.messages, adapter) {
            binding.chatRecyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

}