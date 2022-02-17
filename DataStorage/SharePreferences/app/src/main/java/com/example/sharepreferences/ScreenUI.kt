package com.example.sharepreferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sharepreferences.Model.UserModel
import com.example.sharepreferences.Utils.SharePreferenceUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Composable
fun ScreenUI() {
    var textStr by remember{ mutableStateOf("")}
    fun saveUser(){
        val user = UserModel(1,"user 1")
        val gson = Gson()
        val strUserJson = gson.toJson(user)
        SharePreferenceUtils.savePreference("user",strUserJson)
    }
    fun getUser(): UserModel{
        val gson = Gson()
        val strUserJson = SharePreferenceUtils.getPreference("user","")
        return gson.fromJson(strUserJson,UserModel::class.java)
    }
    fun saveListUser(){
        val listUser = listOf<UserModel>(UserModel(1,"user 1"), UserModel(2,"user 2"))
        val gson = Gson()
        val strListUserJson = gson.toJson(listUser)
        SharePreferenceUtils.savePreference("listUser", strListUserJson)
    }
    fun getListUser() : List<UserModel>{
        val gson = Gson()
        val type: Type = object : TypeToken<List<UserModel>>() {}.type
        val strListUserJson = SharePreferenceUtils.getPreference("listUser","")
        return gson.fromJson(strListUserJson,type)
    }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
//            saveUser()
            saveListUser()
        }) {
            Text(text = "Save USer")
        }
        Button(onClick = {
//            textStr = getUser().toString()
            textStr = getListUser().toString()
        }) {
            Text(text = "Show")
        }
        Text(text = textStr)
    }
}