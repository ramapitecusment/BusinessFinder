package com.example.businessfinder.database.dao

import androidx.room.*
import com.example.businessfinder.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(order: UserEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(order: UserEntity)

    @Delete
    suspend fun delete(order: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}