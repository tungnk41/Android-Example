package com.example.sharepreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String PREFERENCES_BOOLEAN = "PREFERENCES_BOOLEAN";
    private static final String PREFERENCES_STRING = "PREFERENCES_STRING";
    private Context mContext;

    public Preferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCES_BOOLEAN,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCES_STRING,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
