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

    fun insertDefaultUser() {
        viewModelScope.launch {
            repository.insertUser(
                UserInfo(name = "admin", password = "1234")
            )
        }
    }
}