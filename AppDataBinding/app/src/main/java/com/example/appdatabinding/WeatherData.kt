package com.example.appdatabinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField




class WeatherData(location: String, temperature: String) : BaseObservable() {

    var m_location: String = location
    var m_temperature: String = temperature
        private set

    @Bindable
    fun getTemperature(): String{
        return m_temperature
    }

    @Bindable
    fun getLocation(): String{
        return m_location
    }

    fun setLocation(location : String){
        m_location = location
        notifyPropertyChanged(BR.location)
    }

    fun setTemperature(temperature: String){
        m_temperature = temperature
        notifyPropertyChanged(BR.temperature)
    }
}

//
//private class TemperatureData {
//    val celsius = ObservableField<String>()
//    val location = ObservableField<String>()
//}