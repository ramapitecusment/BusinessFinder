package com.example.businessfinder.scenes.chat

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Message
import com.example.businessfinder.models.MessageType
import com.example.businessfinder.models.TextMessage
import com.example.businessfinder.models.User
import com.example.businessfinder.services.ChatService
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatViewModel(
    private val userService: UserService,
    private val chatService: ChatService
) : BaseViewModel() {

    private var otherUserUID: String? = null

    val title = MutableStateFlow("")
    val otherUser = MutableStateFlow<User?>(null)
    val messages = MutableStateFlow<List<Message>>(emptyList())

    fun init(userUID: String) {
        if (this.otherUserUID != null) return
        this.otherUserUID = userUID
        loadingResult()
        getOtherUser()
        observeMessages()
    }

    private fun getOtherUser() {
        userService.getUser(otherUserUID!!).onEach { snapshot ->
            val user = snapshot.toObject(User::class.java)!!
            otherUser.value = user
            title.value = user.companyName
            successResult()
        }.launchIn(viewModelScope)
    }

    private fun observeMessages() {
        chatService.getOrCreateChatChannel(otherUserUID!!) { chatChannel ->
            chatService.getMessages(chatChannel).onEach { query ->
                val messages = mutableListOf<Message>()
                query.documents.forEach {
                    when (it["type"]) {
                        MessageType.TEXT -> messages.add(it.toObject(TextMessage::class.java)!!)
                        MessageType.IMAGE -> {}
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}