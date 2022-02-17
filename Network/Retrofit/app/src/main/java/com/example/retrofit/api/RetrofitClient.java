package com.example.retrofit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();
    String BASE_URL_CURRENCY = "http://apilayer.net/";
    String BASE_URL_POSTS = "https://jsonplaceholder.typicode.com/";

    private static RetrofitClient instance = null;

    private RetrofitClient(){ }

    public static RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_POSTS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /*Kotlin
     inline fun <refied T> getService() : T = getRetrofit().create(T::class.java)

     call : RetrofitClient().getInstance().getService<RemoteService>()
     */

}
