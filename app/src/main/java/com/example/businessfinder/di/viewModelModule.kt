package com.example.businessfinder.di

import com.example.businessfinder.scenes.login.LoginViewModel
import com.example.businessfinder.scenes.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }

}