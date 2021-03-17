package com.example.openweather.Service;


import android.content.Context;


import java.util.ArrayList;
import java.util.List;

public class CoordinateInfo {

    public static List<String> getCurrentCoordinateInfo(Context context){
        List<String> result = new ArrayList<>(); //Contain lat,lon value
        double latitude = 0;
        double longitude = 0;
        GpsTracker gpsTracker = new GpsTracker(context);
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
