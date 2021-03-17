package com.example.openweather.DataLocal;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String PREFERENCES_STRING = "PREFERENCES_STRING";
    private static final String PREFERENCES_BOOLEAN = "PREFERENCES_BOOLEAN";
    private Context context;

    public Preferences(Context context) {
        this.context = context;
    }


    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public void putBooleanValue(String key, Boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}
