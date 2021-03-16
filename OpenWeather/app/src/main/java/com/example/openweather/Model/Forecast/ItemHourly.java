package com.example.openweather.Model.Forecast;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemHourly {
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "List{" +
                "clouds=" + clouds +
                ", wind=" + wind +
                ", main=" + main +
                ", listWeather=" + weather +
                '}';
    }
}
