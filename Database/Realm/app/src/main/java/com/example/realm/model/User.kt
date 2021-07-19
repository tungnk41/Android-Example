package com.example.realm.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/*
* PrimaryKey	: The @PrimaryKey annotation will mark a field as a primary key inside Realm.
* Required	    : This annotation will mark the field or the element in RealmList as not nullable.
* Index	        : This annotation will add a search index to the field.
* Ignore
* */
data class User(
    @PrimaryKey var id: Int,
    @Required var name: String,
    var age: Int,
    var listGame: RealmList<Game>
) : RealmObject() {

}