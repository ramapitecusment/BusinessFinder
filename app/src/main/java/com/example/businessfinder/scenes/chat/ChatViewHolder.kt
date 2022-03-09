package com.example.businessfinder.scenes.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.databinding.ItemChatMessageBinding
import com.example.businessfinder.models.Message

class ChatViewHolder(
    private val binding: ItemChatMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {

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