package com.example.businessfinder.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ioDispatcher = "ioDispatcher"
const val mainDispatcher = "mainDispatcher"

val coroutineModule = module {
    single(named(ioDispatcher)) { Dispatchers.IO }
    single(named(mainDispatcher)) { Dispatchers.Main }
}

