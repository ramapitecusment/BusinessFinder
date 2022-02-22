package com.example.businessfinder.di

import com.example.businessfinder.services.LoginService
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginService() }
}