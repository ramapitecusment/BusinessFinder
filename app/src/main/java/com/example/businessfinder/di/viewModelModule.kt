package com.example.businessfinder.di

import com.example.businessfinder.scenes.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { LoginViewModel() }

}