package com.example.openweather.DataLocal;

import android.content.Context;

import com.example.openweather.AppWeather;

public class DataLocalManager {

    static final String LASTEST_LOCATION = "LASTEST_LOCATION";
    private static DataLocalManager instance;
    private Preferences preferences;

    private DataLocalManager(){

    }

    public static synchronized DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
            instance.preferences = new Preferences(AppWeather.getContext());
        }
        return instance;
    }

    public  void saveLocation(String location){
        DataLocalManager.getInstance().preferences.putStringValue(LASTEST_LOCATION,location);
    }

    public  String getLastestLocation(){
        return DataLocalManager.getInstance().preferences.getStringValue(LASTEST_LOCATION);
    }

}
