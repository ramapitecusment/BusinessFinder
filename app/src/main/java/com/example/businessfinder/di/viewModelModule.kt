package com.example.businessfinder.di

import com.example.businessfinder.scenes.chat.ChatViewModel
import com.example.businessfinder.scenes.chatchannels.ChatChannelsViewModel
import com.example.businessfinder.scenes.login.LoginViewModel
import com.example.businessfinder.scenes.profile.ProfileViewModel
import com.example.businessfinder.scenes.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { ChatChannelsViewModel(get()) }
    viewModel { ChatViewModel(get(), get()) }
}