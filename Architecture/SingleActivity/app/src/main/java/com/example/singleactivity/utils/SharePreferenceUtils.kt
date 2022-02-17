package com.example.singleactivity.utils

import android.content.Context
import com.example.singleactivity.App

object SharePreferenceUtils {
    private const val PREFERENCE_APP = "APP_DEMO"
    fun <T> savePreference(key: String, value: T){
        val sharePref = App.appContext.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE)
        with(sharePref.edit()){
            when(value){
                is String -> putString(key,value)
                is Boolean -> putBoolean(key, value)
                else->{}
            }
            apply()
        }
    }

    fun <T> getPreference(key: String, defaultValue: T) :T? {
        val sharePref = App.appContext.getSharedPreferences(PREFERENCE_APP,Context.MODE_PRIVATE)
        with(sharePref){
            when(defaultValue){
                is String -> return getString(key,defaultValue) as T
                is Boolean -> return getBoolean(key,defaultValue) as T
                else -> {}
            }
            return null
        }
    }
}