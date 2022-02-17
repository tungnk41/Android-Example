package com.example.snippet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    var count = MutableLiveData<Int>(0)

    fun inc() {
        count.value = count.value?.plus(1)
        Log.d("TAG", "inc: ${count.value}")
    }

    fun copy(){
        count = MutableLiveData(1)
    }
}