package com.example.authtesting.repository

import com.example.authtesting.roomDB.UserDao
import com.example.authtesting.roomDB.UserInfo

class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): UserInfo? {
        return userDao.login(username, password)
    }

    suspend fun insertUser(userInfo: UserInfo) {
        userDao.insertUser(userInfo)
    }

    suspend fun getUserByUsername(username: String): UserInfo? {
        return userDao.getUserByUsername(username)
    }
}
