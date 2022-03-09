package com.example.businessfinder.scenes.chatchannels

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.User
import com.example.businessfinder.services.FirebaseServices
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatChannelsViewModel(
    private val userService: UserService,
) : BaseViewModel() {
    val usersList = MutableStateFlow<List<User>>(listOf())

    init {
        userService.getAllUsers().onEach { query ->
            val users = mutableListOf<User>()
            query.documents.forEach { document ->
                if (document.id != FirebaseServices.auth.currentUser?.uid)
                    users.add(document.toObject(User::class.java)!!)
            }
            usersList.value = users.toList()
        }.launchIn(viewModelScope)
    }

}