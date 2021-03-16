package com.example.openweather.Model.OpenWeather;

import com.example.openweather.Model.Forecast.Forecast;

public interface ForecastWeatherResult {
    void onSuccess(Forecast forecastWeather);
    void onFailed();
}
