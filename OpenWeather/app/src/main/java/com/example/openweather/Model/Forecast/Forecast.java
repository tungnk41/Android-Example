package com.example.openweather.Model.Forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("list")
    private List<ItemHourly> list;

    public List<ItemHourly> getList() {
        return list;
    }

    public void setList(List<ItemHourly> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "list=" + list +
                '}';
    }
}
