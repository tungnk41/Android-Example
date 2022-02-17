package com.example.singleactivity.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private val BASE_URL = "http://apilayer.net/"

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private fun provideOkHttpClient( httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    fun provideRetrofitBuilder(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(provideOkHttpClient(provideHttpLoggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> getService(): T =
        provideRetrofitBuilder().create(T::class.java)
}