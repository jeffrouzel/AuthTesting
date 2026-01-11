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

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            _loginResult.postValue(user != null)
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
