package com.example.countstep

import android.content.Context

object SharePrefUtil {
    private const val PREFERENCE_APP = "com.example.countstep"
    fun <T> savePreference(context : Context, key: String, value: T){
        val sharePref = context.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE)
        with(sharePref.edit()){
            when(value){
                is String -> putString(key,value)
                is Boolean -> putBoolean(key, value)
                else->{}
            }
            apply()
        }
    }

    fun <T> getPreference(context : Context, key: String, defaultValue: T) :T? {
        val sharePref = context.getSharedPreferences(PREFERENCE_APP,Context.MODE_PRIVATE)
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