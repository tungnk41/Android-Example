package com.example.retrofit.api;

import com.example.retrofit.model.Currency;
import com.example.retrofit.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/*
http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
* */
public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();
    String BASE_URL_CURRENCY = "http://apilayer.net/";
    String BASE_URL_POSTS = "https://jsonplaceholder.typicode.com/";

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL_POSTS)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/live")
    Call<Currency> convertUsdtoVnd(@Query("access_key") String access_key,
                                   @Query("currencies") String currency,
                                   @Query("source") String source,
                                   @Query("format") int format);

    @GET("api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1")
    Call<Currency> convertUsdtoVnd();

    @GET("api/live")
    Call<Currency> convertUsdtoVnd(@QueryMap Map<String, String> options);



    //https://jsonplaceholder.typicode.com/posts
    @POST("posts")
    Call<User> createUser(@Body User user);

}
