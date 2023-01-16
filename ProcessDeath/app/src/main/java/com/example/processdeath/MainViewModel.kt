package com.example.processdeath

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle?) : ViewModel(){
    var dataViewModel : MutableLiveData<Int> = MutableLiveData<Int>()

    init {
        Log.d(TAG, "MainViewModel : " + (savedStateHandle?.get("data") ?: 0))
        savedStateHandle?.let {
            dataViewModel.value = it["data"]
        }
    }

    fun setData(data: Int) {
        dataViewModel.value = data
        savedStateHandle?.set("data", data)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "onCleared: ")
    }
}