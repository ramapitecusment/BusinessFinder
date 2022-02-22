package com.example.businessfinder.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class AppDatabaseTypeConverters {

    @TypeConverter
    fun fromDate(value: Date): String = Gson().toJson(value)

    @TypeConverter
    fun toDate(value: String): Date = Gson().fromJson(value, object : TypeToken<Date>() {}.type)

    @TypeConverter
    fun fromListString(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toListString(value: String): List<String> = Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)

}