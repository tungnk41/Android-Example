package com.example.sqlite

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite.database.UserDatabase
import com.example.sqlite.model.User


class MainActivity : AppCompatActivity() {
    private lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDataDB()

        button = findViewById(R.id.btnGet)
        button.setOnClickListener {
            getDataDB()
        }
    }

    private fun initDataDB() {
        //UserDatabase.getInstance().userDAO.deleteAll();

        //Add data with custome UserDatabase
        val user = User("abc", "add")
        UserDatabase.instance?.userDAO?.insertUser(user)
    }

    private fun getDataDB() {

        val listUser: List<User>? = UserDatabase.instance?.userDAO?.getListUser()
        if (listUser?.size != 0) {
            for (i in listUser?.indices!!) {
                val user = listUser[i]
                Log.d(TAG, "getDataDB: name : " + user.name + "  , address : " + user.address)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UserDatabase.instance?.close()
    }
}