package com.example.businessfinder.di

import android.content.Context
import androidx.room.Room
import com.example.businessfinder.database.database.AppDatabase
import org.koin.dsl.module

fun databaseModule(context: Context) = module {

    single { get<AppDatabase>().userDao() }

    single {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}