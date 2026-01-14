package com.example.authtesting

import android.app.Application
import androidx.lifecycle.*
import com.example.authtesting.repository.UserRepository
import com.example.authtesting.roomDB.AppDatabase
import com.example.authtesting.roomDB.UserInfo
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao =
        AppDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    // Add this to track the currently logged-in user
    private val _currentUser = MutableLiveData<UserInfo?>()
    val currentUser: LiveData<UserInfo?> = _currentUser

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            _loginResult.postValue(user != null)
            // Store the logged-in user
            if (user != null) {
                _currentUser.postValue(user)
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            // Check if user already exists
            val existingUser = repository.getUserByUsername(username)
            if (existingUser != null) {
                _registerResult.postValue(RegisterResult.USER_EXISTS)
            } else {
                repository.insertUser(UserInfo(name = username, password = password))
                _registerResult.postValue(RegisterResult.SUCCESS)
            }
        }
    }

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    enum class RegisterResult {
        SUCCESS,
        USER_EXISTS
    }

    fun insertDefaultUser() {
        viewModelScope.launch {
            repository.insertUser(
                UserInfo(name = "admin", password = "1234")
            )
        }
    }
}