package com.example.openweather.Utils;

import com.example.openweather.Model.CurrentWeather.CurrentWeather;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WeatherUtil {
    static WeatherUtil instance;

    private WeatherUtil() {
    }

    public static synchronized WeatherUtil getInstance(){
        if(instance == null){
            instance = new WeatherUtil();
        }

        return instance;
    }

    //Convert unix time to hour
    public String timeFormatter(int milliseconds){
        Date date = new Date(milliseconds *1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

}
