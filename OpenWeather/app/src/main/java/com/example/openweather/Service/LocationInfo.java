package com.example.openweather.Service;


import com.example.openweather.AppWeather;
import com.example.openweather.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LocationInfo {

    public static List<String> getCurrentLocationInfo(){
        List<String> result = new ArrayList<>(); //Contain lat,lon value
        double latitude = 0;
        double longitude = 0;
        GpsTracker gpsTracker = new GpsTracker(AppWeather.getContext());
        if(gpsTracker.isLocationAvailable()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }else{
            gpsTracker.showSettingsAlert();
        }
        result.add(String.valueOf(latitude));
        result.add(String.valueOf(longitude));
        return result;
    }
}
