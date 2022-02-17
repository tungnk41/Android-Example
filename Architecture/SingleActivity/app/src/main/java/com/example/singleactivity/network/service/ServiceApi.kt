package com.example.singleactivity.network.service

import com.example.singleactivity.model.Currency
import retrofit2.Call
import retrofit2.http.GET




interface ServiceApi {

    @GET("api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1")
    fun getCurrentcy(): Call<Currency>
}