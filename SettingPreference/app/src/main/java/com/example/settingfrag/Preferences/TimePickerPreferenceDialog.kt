package com.example.settingfrag.Preferences

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.preference.DialogPreference
import androidx.preference.PreferenceDialogFragmentCompat
import java.text.DateFormat

class TimePickerPreferenceDialog : PreferenceDialogFragmentCompat() {
    private lateinit var timePicker: TimePicker

    override fun onCreateDialogView(context: Context?): View {
        super.onCreateDialogView(context)
        timePicker = TimePicker(context)
        return timePicker
    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        val minutesAfterMidnight = (preference as TimePickerPreference).getPersistedMinutesFromMidnight()
        val hours = minutesAfterMidnight/60
        val minutes = minutesAfterMidnight%60
        val is24Hour = true

        timePicker.apply {
            setIs24HourView(is24Hour)
            hour = hours
            minute = minutes
        }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if(positiveResult) {
            val minutesAfterMidnight = (timePicker.hour * 60) + timePicker.minute
            if(minutesAfterMidnight < 30){
                Toast.makeText(context,"Please set minimun minutes is 30",Toast.LENGTH_SHORT).show()
            }else{
                (preference as TimePickerPreference).persistMinutesFromMidnight(minutesAfterMidnight)
            }
        }
    }

    companion object{
        fun newInstance(key : String) : TimePickerPreferenceDialog{
            val fragment = TimePickerPreferenceDialog()
            val bundle = Bundle(1)
            bundle.putString(ARG_KEY,key)
            fragment.arguments = bundle

            return fragment
        }
    }

}