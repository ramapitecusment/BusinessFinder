package com.example.businessfinder.di

import com.example.businessfinder.services.UserService
import org.koin.dsl.module

val repositoryModule = module {
    single { UserService() }
}