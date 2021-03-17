package com.example.openweather.Model.OpenWeather;

import android.content.Context;
import android.util.Log;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;
import com.example.openweather.R;
import com.example.openweather.Service.WeatherService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OpenWeather {
    private static final String TAG = "OpenWeather";
    private CurrentWeatherResult currentWeatherResult;

    public static void currentWeatherByLocation(Context context, String location, CurrentWeatherResult weatherResult) {
        String api_key = context.getResources().getString(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("q",location);
        options.put("units","metric");
        options.put("lang","en");
        options.put("appid",api_key);
        WeatherService.weatherService.getCurrentWeatherByLocation(options)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        CurrentWeather currentWeather = response.body();
                        if(currentWeather != null){
                            weatherResult.onSuccess(currentWeather);
                            Log.d(TAG, "onResponse: getCurrentWeatherByName Successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        weatherResult.onFailed();
                        Log.d(TAG, "onFailure: getCurrentWeatherByName Failed");
                    }
                });
    }


    public static void forecastWeatherByName(Context context, String location,ForecastWeatherResult weatherResult) {
        String api_key = context.getResources().getString(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("q",location);
        options.put("units","metric");
        options.put("lang","en");
        options.put("cnt","5");
        options.put("appid",api_key);

        WeatherService.weatherService.getForecastWeatherByLocation(options)
                .enqueue(new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                        Forecast forecastWeather = response.body();
                        if(forecastWeather != null){
                            weatherResult.onSuccess(forecastWeather);
                            Log.d(TAG, "onResponse: getForecastWeatherByName Successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Forecast> call, Throwable t) {
                        weatherResult.onFailed();
                        Log.d(TAG, "onFailure: getForecastWeatherByName Failed");
                    }
                });
    }

    /* Get by Location */

    public static void currentWeatherByCoordinate(Context context, String latitude,String longitude, CurrentWeatherResult weatherResult) {
        String api_key = context.getResources().getString(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("lat",latitude);
        options.put("lon",longitude);
        options.put("units","metric");
        options.put("lang","en");
        options.put("appid",api_key);
        WeatherService.weatherService.getCurrentWeatherByCoordinate(options)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        CurrentWeather currentWeather = response.body();
                        if(currentWeather != null){
                            weatherResult.onSuccess(currentWeather);
                            Log.d(TAG, "onResponse: getCurrentWeatherByName Successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        weatherResult.onFailed();
                        Log.d(TAG, "onFailure: getCurrentWeatherByName Failed");
                    }
                });
    }


    public static void forecastWeatherByCoordinate(Context context, String latitude,String longitude, ForecastWeatherResult weatherResult) {
        String api_key = context.getResources().getString(R.string.api_key);
        Map<String,String> options = new HashMap<>();
        options.put("lat",latitude);
        options.put("lon",longitude);
        options.put("units","metric");
        options.put("lang","en");
        options.put("cnt","5");
        options.put("appid",api_key);

        WeatherService.weatherService.getForecastWeatherByCoordinate(options)
                .enqueue(new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                        Forecast forecastWeather = response.body();
                        if(forecastWeather != null){
                            weatherResult.onSuccess(forecastWeather);
                            Log.d(TAG, "onResponse: getForecastWeatherByName Successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Forecast> call, Throwable t) {
                        weatherResult.onFailed();
                        Log.d(TAG, "onFailure: getForecastWeatherByName Failed");
                    }
                });
    }

}
