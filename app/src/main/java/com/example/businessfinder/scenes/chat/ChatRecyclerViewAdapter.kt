package com.example.businessfinder.scenes.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.businessfinder.models.Message
import com.google.firebase.auth.FirebaseAuth

class ChatRecyclerViewAdapter(private val currentUser: String) : ListAdapter<Message, ChatViewHolder>(ChatCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder = ChatViewHolder.from(parent)

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position), currentUser)
    }
}

class ChatCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.time == newItem.time && oldItem.senderId == newItem.senderId && oldItem.type == newItem.type
    }
}