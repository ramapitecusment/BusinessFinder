package com.example.businessfinder.scenes.login

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.common.Constants.notEmpty
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.businessfinder.models.Result
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userService: UserService
) : BaseViewModel() {

    val emailFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")

    val emailErrorFlow = MutableStateFlow<String?>(null)
    val passwordErrorFlow = MutableStateFlow<String?>(null)

    val navigateProfileScreen = MutableSharedFlow<Unit>()
    val navigateRegistrationScreen = MutableSharedFlow<Unit>(0)

    fun onSignInClicked() {
        if (emailFlow.value.trim().isNotEmpty() && passwordFlow.value.trim().isNotEmpty()) {
            userService.signInUserFlow(emailFlow.value.trim(), passwordFlow.value.trim()).onEach {
                when (it) {
                    is Result.Success -> signInSuccess()
                    is Result.Failure -> signInFailure(it.msg)
                    else -> {}
                }
            }.launchIn(viewModelScope)
        } else {
            emailErrorFlow.value = notEmpty
            passwordErrorFlow.value = notEmpty
        }
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            navigateRegistrationScreen.emit(Unit)
        }
    }

    private fun signInSuccess() {
        viewModelScope.launch {
            navigateProfileScreen.emit(Unit)
        }
    }

    private fun signInFailure(e: Throwable) {
        when (e) {
            is FirebaseAuthInvalidUserException -> {
                emailErrorFlow.value = "Данный пользователь не обнаружен"
            }
            is FirebaseAuthInvalidCredentialsException -> {
                passwordErrorFlow.value = "Неверный пароль"
            }
            else -> {
                viewModelScope.launch {
                    showToast.emit("Exception: $e")
                }
            }
        }
    }
}