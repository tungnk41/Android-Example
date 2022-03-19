package com.example.realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.realm.model.Game
import com.example.realm.model.User
import com.example.realm.model.UserDao
import io.realm.Realm
import io.realm.RealmList

class MainActivity : AppCompatActivity() {
    val realm: Realm? = Realm.getDefaultInstance()
    val userDao = UserDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDao.insert(User(name = "user 1", age = 16, listGame = RealmList(Game("game 1"))))
    }



    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
        userDao.close()
    }
}