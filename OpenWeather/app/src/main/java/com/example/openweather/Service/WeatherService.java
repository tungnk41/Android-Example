package com.example.openweather.Service;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherService {
    Gson gson  = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();
    //https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=3b4d3ad9dac72a8da6c42eb87d2aecb1

    String BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/";

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    WeatherService weatherService = new Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WeatherService.class);


    /*
    * location : HaNoi
    * units : metric (Celsius) , imperial (Fahrenheit), default: kevin
    * lang : vi, en
    * */
    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherByLocation(@QueryMap Map<String,String> options);

    @GET("forecast")
    Call<Forecast> getForecastWeatherByLocation(@QueryMap Map<String,String> options);


    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherByCoordinate(@QueryMap Map<String,String> options);

    @GET("forecast")
    Call<Forecast> getForecastWeatherByCoordinate(@QueryMap Map<String,String> options);


}
