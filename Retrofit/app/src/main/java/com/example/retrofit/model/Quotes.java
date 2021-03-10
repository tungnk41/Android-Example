package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Quotes {
    @SerializedName("USDVND") //Gson lib
    private float usdVnd;

    public float getUsdVnd() {
        return usdVnd;
    }

    public void setUsdVnd(float usdVnd) {
        this.usdVnd = usdVnd;
    }
}
