package com.example.businessfinder.scenes.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.common.extensions.toTime
import com.example.businessfinder.databinding.ItemChatMessageBinding
import com.example.businessfinder.models.Message
import com.example.businessfinder.models.TextMessage
import java.util.*

class ChatViewHolder(
    private val binding: ItemChatMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message, currentUser: String) {
        with(binding) {
            chatLayout.apply {
                val params = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    if (message.senderId == currentUser) Gravity.END else Gravity.START
                )
                layoutParams = params
            }
            if (message is TextMessage) messageText.text = message.text
            messageTime.text = Date(message.time).toTime()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ChatViewHolder = ChatViewHolder(
            ItemChatMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}