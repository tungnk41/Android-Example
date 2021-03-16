package com.example.openweather.Model.Forecast;

public class Wind {
    private double speed;
    private int deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeedl(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speedl=" + speed +
                ", deg=" + deg +
                '}';
    }
}
