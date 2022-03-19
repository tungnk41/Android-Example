package com.example.settingfrag.Preferences

import android.content.Context
import android.content.res.TypedArray
import android.icu.number.IntegerWidth
import android.util.AttributeSet
import android.widget.TimePicker
import androidx.preference.DialogPreference
import androidx.preference.Preference

class TimePickerPreference(context : Context, attrs : AttributeSet) : DialogPreference(context, attrs) {

    companion object{
        const val DEFAULT_MINUTES_FROM_MIDNIGHT = 0
    }


    fun getPersistedMinutesFromMidnight(): Int {
        return getPersistedInt(DEFAULT_MINUTES_FROM_MIDNIGHT)
    }

    fun persistMinutesFromMidnight(minutesFromMidnight: Int) {
        persistInt(minutesFromMidnight)
        summary = minutesFromMidnightToHourlyTime(minutesFromMidnight)
        callChangeListener(minutesFromMidnight)
    }

    fun minutesFromMidnightToHourlyTime(minutesFromMidnight: Int) : String{
        val hour : String = if(minutesFromMidnight/60 < 10) "0" + (minutesFromMidnight/60).toString() else (minutesFromMidnight/60).toString()
        val minute : String = if(minutesFromMidnight%60 < 10) "0" + (minutesFromMidnight%60).toString() else (minutesFromMidnight%60).toString()
        val time : String = hour+ " : " + minute
        return time
    }

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any {
        return a?.getInt(index, DEFAULT_MINUTES_FROM_MIDNIGHT) as Int
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)
        if(defaultValue != null){
            persistMinutesFromMidnight(defaultValue as Int)
        }

    }
}