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
class MainViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    var dataViewModel : MutableLiveData<String> = MutableLiveData<String>()

    init {
        Log.d(TAG, "MainViewModel : " + savedStateHandle["data"])
    }

    fun setData(data: String) {
        dataViewModel.value = data
//        savedStateHandle["data"] = data
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "onCleared: ")
    }
}