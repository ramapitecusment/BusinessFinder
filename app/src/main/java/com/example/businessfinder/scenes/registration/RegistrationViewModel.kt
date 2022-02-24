package com.example.businessfinder.scenes.registration

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val userService: UserService
) : BaseViewModel() {

    fun onSignUpClicked() {
        viewModelScope.launch {

        }
    }
}