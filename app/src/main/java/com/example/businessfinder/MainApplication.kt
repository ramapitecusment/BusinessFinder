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

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            koin.loadModules(
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