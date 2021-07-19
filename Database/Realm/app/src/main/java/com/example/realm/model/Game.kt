package com.example.realm.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

data class Game(@PrimaryKey @Required var name : String = "") : RealmObject() {


}