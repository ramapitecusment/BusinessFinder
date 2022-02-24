package com.example.businessfinder.scenes.profile

import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel(
    private val userService: UserService
) : BaseViewModel() {

    val photoUrlFlow = MutableStateFlow("")
    val companyNameFlow = MutableStateFlow("")
    val binFlow = MutableStateFlow("")
    val emailFlow = MutableStateFlow("")

    init {

    }

    fun start() {

    }
}