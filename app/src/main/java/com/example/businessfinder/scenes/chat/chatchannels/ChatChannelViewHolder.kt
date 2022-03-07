package com.example.businessfinder.scenes.chat.chatchannels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.common.extensions.glideProfileFirebaseImage
import com.example.businessfinder.databinding.ItemChatChannelBinding
import com.example.businessfinder.models.User

class ChatChannelViewHolder(
    private val binding: ItemChatChannelBinding,
    private val onClick: (userUID: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.chatChannelItem.setOnClickListener { onClick }
    }

    fun bind(user: User) {
        with(binding) {
            username.text = user.companyName
            userEmail.text = user.email
            userImageView.glideProfileFirebaseImage(user.photoUrl)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (userUID: String) -> Unit): ChatChannelViewHolder = ChatChannelViewHolder(
            ItemChatChannelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }
}