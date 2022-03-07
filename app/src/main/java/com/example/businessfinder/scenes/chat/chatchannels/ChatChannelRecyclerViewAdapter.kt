package com.example.businessfinder.scenes.chat.chatchannels

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.businessfinder.models.User

class ChatChannelRecyclerViewAdapter(
    private val onClick: (userUID: String) -> Unit
) : ListAdapter<User, ChatChannelViewHolder>(ChatChannelCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatChannelViewHolder.from(parent, onClick)

    override fun onBindViewHolder(holder: ChatChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChatChannelCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.firebaseUID == newItem.firebaseUID
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}