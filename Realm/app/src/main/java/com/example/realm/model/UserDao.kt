package com.example.realm.model

import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class UserDao {
    private val realm = Realm.getDefaultInstance()

    fun insert(user : User){
        realm.executeTransaction( Realm.Transaction {
            it.insertOrUpdate(user)
        })
    }

    fun getAll() : List<User>{
        val realmResults = realm
            .where(User::class.java)
            .findAll()
        var results : List<User>  = ArrayList<User>(realmResults)
        return results
    }

    fun delete(id : Int){
        realm.executeTransaction( Realm.Transaction {
            val realmResults : RealmResults<User>? = realm
                .where(User::class.java)
                .equalTo("id",id)
                .findAll()
            realmResults?.deleteAllFromRealm()
        })
    }

    fun deleteAll(){
        realm.executeTransaction( Realm.Transaction {
            val realmResults : RealmResults<User>? = realm
                .where(User::class.java)
                .findAll()
            realmResults?.deleteAllFromRealm()
        })
    }

    fun close(){
        realm?.close()
    }
}