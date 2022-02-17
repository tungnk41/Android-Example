package com.example.settingfrag

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.preference.*
import com.example.settingfrag.Preferences.TimePickerPreference
import com.example.settingfrag.Preferences.TimePickerPreferenceDialog


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var edtPreference : EditTextPreference
    private lateinit var listPreference : ListPreference
    private lateinit var swtPreference : SwitchPreferenceCompat
    private lateinit var dgTimePicker : TimePickerPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        edtPreference = preferenceManager.findPreference("edt")!!
        listPreference = preferenceManager.findPreference("list")!!
        swtPreference = preferenceManager.findPreference("swt")!!
        dgTimePicker = preferenceManager.findPreference("dgTimePicker")!!


        edtPreference.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                Log.d("TAG", "onPreferenceChange: " + newValue)
                return true
            }
        })

        listPreference.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                Log.d("TAG", "onPreferenceChange: " + newValue)
                return true
            }
        })

        swtPreference.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                Log.d("TAG", "onPreferenceChange: " + newValue)
                return true
            }
        })

        dgTimePicker.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                Log.d("TAG", "onPreferenceChange: " + newValue.toString())
                return true
            }

        })

    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if(preference is TimePickerPreference){
            val timepickerdialog = TimePickerPreferenceDialog.newInstance(preference.key)
            timepickerdialog.setTargetFragment(this,0)
            timepickerdialog.show(parentFragmentManager, "TimePickerDialog")
        }else{
            super.onDisplayPreferenceDialog(preference)
        }
    }
}
