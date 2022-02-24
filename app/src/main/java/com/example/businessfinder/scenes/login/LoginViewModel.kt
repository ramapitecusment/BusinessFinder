package com.example.businessfinder.scenes.login

import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.common.Constants.notEmpty
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.example.businessfinder.models.Result
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class LoginViewModel(
    private val userService: UserService
) : BaseViewModel() {

    val emailFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")

    val emailErrorFlow = MutableStateFlow("")
    val passwordErrorFlow = MutableStateFlow("")

    val navigateRegistrationScreen = MutableSharedFlow<Unit>()
    val navigateMainScreen = MutableSharedFlow<Unit>()

    fun onSignInClicked() {
        ioScope.launch {
            if (emailFlow.value.trim().isNotEmpty() && passwordFlow.value.trim().isNotEmpty()) {
                when (val result = userService.signInUser(emailFlow.value.trim(), passwordFlow.value.trim())) {
                    is Result.Success -> signInSuccess()
                    is Result.Failure -> signInFailure(result.msg)
                    else -> {}
                }
            } else {
                emailErrorFlow.value = notEmpty
                passwordErrorFlow.value = notEmpty
            }
        }
    }

    fun onSignUpClicked() {
        navigateRegistrationScreen.tryEmit(Unit)
    }

    private fun signInSuccess() {
        navigateMainScreen.tryEmit(Unit)
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
                showToast.tryEmit("Exception: $e")
            }
        }
    }
}