package com.example.authtesting.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserInfo)

    @Query("SELECT * FROM users_table WHERE username = :username AND password = :password")
    suspend fun getAllUsers(): List<UserInfo>
}