package com.example.openweather.Model.OpenWeather;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;

public interface CurrentWeatherResult {
    void onSuccess(CurrentWeather currentWeather);
    void onFailed();
}
