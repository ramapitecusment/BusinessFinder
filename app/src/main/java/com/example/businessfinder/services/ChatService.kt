package com.example.businessfinder.services

import com.example.businessfinder.common.Constants
import com.example.businessfinder.common.Constants.KEY_ENGAGED_CHAT_CHANNELS
import com.example.businessfinder.common.extensions.snapshotAsFlow
import com.example.businessfinder.models.ChatChannel
import com.example.businessfinder.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChatService {

    fun getOrCreateChatChannel(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        FirebaseServices.currentUserDocument(FirebaseServices.auth.uid!!)
            .collection(Constants.KEY_ENGAGED_CHAT_CHANNELS)
            .document(otherUserId).get().addOnSuccessListener {
                if (it.exists()) onComplete(it["channelId"] as String)
                else {
                    val currentUserId = FirebaseServices.auth.currentUser!!.uid
                    val newChannel = FirebaseServices.chatChannelsCollection.document()
                    newChannel.set(ChatChannel(listOf(currentUserId, otherUserId)))

                    FirebaseServices.currentUserDocument(currentUserId)
                        .collection(KEY_ENGAGED_CHAT_CHANNELS)
                        .document(otherUserId)
                        .set(mapOf("channelId" to newChannel.id))

                    FirebaseServices.usersCollection.document(otherUserId)
                        .collection(KEY_ENGAGED_CHAT_CHANNELS)
                        .document(currentUserId)
                        .set(mapOf("channelId" to newChannel.id))
                    onComplete(newChannel.id)
                }
            }
    }

    fun getMessages(channelId: String) = FirebaseServices.currentChatChannelMessages(channelId).snapshotAsFlow()

}