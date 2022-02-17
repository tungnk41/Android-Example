package com.example.singleactivity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.singleactivity.network.RetrofitClient
import com.example.singleactivity.network.service.ServiceApi
import com.example.singleactivity.network.service.enqueueWithLog

class MainViewModel : ViewModel() {
    fun getCurrency(){
        RetrofitClient.getService<ServiceApi>().getCurrentcy().enqueueWithLog(
            onSuccess = {res ->
                val term = res.body()?.terms.toString()
                Log.d("TAG", "onSuccess : getCurrency: $term")
            },
            onFailure = {
                Log.d("TAG", "onFailure : getCurrency")
            }
        )
    }
}