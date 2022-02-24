package com.example.businessfinder

import android.app.Application
import com.example.businessfinder.di.coroutineModule
import com.example.businessfinder.di.databaseModule
import com.example.businessfinder.di.repositoryModule
import com.example.businessfinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    databaseModule(this@MainApplication),
                    coroutineModule
                )
            )
        }
    }

    companion object {
        lateinit var instance: MainApplication
    }
}