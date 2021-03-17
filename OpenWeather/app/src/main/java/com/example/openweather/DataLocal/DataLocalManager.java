package com.example.openweather.DataLocal;

import android.content.Context;

import com.example.openweather.AppWeather;

public class DataLocalManager {

    static final String LASTEST_LOCATION = "LASTEST_LOCATION";
    static final String LASTEST_UPDATE_BY_LOCATION = "LASTEST_UPDATE_BY_LOCATION";
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

    public  String getLastestSaveLocation(){
        return DataLocalManager.getInstance().preferences.getStringValue(LASTEST_LOCATION);
    }

    public void setLastestUpdateByLocation(){
        DataLocalManager.getInstance().preferences.putBooleanValue(LASTEST_UPDATE_BY_LOCATION,true);
    }

    public Boolean isLastestUpdateByLocation(){
        return DataLocalManager.getInstance().preferences.getBooleanValue(LASTEST_UPDATE_BY_LOCATION);
    }

}
