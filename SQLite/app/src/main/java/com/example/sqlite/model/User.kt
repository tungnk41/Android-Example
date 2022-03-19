package com.example.sqlite.model

import kotlin.properties.Delegates

class User() {
    var id : Int = 0
    lateinit var name : String
    lateinit var address: String
    constructor(name : String,address: String) : this(){
        this.name = name
        this.address = address
    }
    constructor(id : Int,name : String,address: String) : this(name,address){
        this.id = id
    }
}