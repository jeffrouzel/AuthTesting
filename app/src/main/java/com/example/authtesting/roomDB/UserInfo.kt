package com.example.authtesting.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users_table")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "username")
    val name: String,

    @ColumnInfo(name = "password")
    val password: String
)
