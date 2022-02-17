package com.example.sqldelight.view.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqldelight.App
import com.example.sqldelight.dao.IUserDAO
import com.example.sqldelight.dao.impl.UserDAO
import database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    val userDAO: IUserDAO = UserDAO(App.database)

    fun login(username: String, password: String,onSuccess: (User)->Unit) {
        Log.d("TAG", "login: " + username + " " + password)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = userDAO.findByNameAndPassword(username,password)
                if (user != null){
                    onSuccess(user)
                }
            }
        }
    }
}