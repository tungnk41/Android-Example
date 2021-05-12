package com.example.greendao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.greendao.model.DaoSession
import com.example.greendao.model.UserEntity
import java.util.*

/*
* Step 1 : Config Build grade Modul app, Project
* Step 2 : Create Entity
* Step 3: Build source code for generate code of greenDao
* Step 4: Create DaoSession in Application file
* */

class MainActivity : AppCompatActivity() {
    private lateinit var daoSession: DaoSession
    private var listUser = ArrayList<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        daoSession = (application as App).getDaoSession()

        test()
    }

    fun addUser(user : UserEntity) {
        val userDao = daoSession.userEntityDao
        userDao.insert(user)
    }

    fun deleteUser(id : Long){
        val userDao = daoSession.userEntityDao
        userDao.deleteByKey(id)
    }

    fun deleteAll(){
        val userDao = daoSession.userEntityDao
        userDao.deleteAll()
    }

    fun getAllUser() : List<UserEntity>{
        val userDao = daoSession.userEntityDao
        return userDao.loadAll()
    }

    /***************************************************************/

    fun test(){
        deleteAll()

        addUser(UserEntity(1,"A"))
        addUser(UserEntity(2,"B"))

        Log.d("TAG", "Add A,B: " + getAllUser().toString())

        deleteUser(2)

        Log.d("TAG", "Delete B: " + getAllUser().toString())

    }
}