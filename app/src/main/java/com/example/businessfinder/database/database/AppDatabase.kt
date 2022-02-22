package com.example.businessfinder.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.businessfinder.database.AppDatabaseTypeConverters
import com.example.businessfinder.database.dao.UserDao
import com.example.businessfinder.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(AppDatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}