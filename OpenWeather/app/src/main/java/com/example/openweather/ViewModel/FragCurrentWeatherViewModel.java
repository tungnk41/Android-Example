package com.example.openweather.ViewModel;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.openweather.AppWeather;
import com.example.openweather.DataLocal.DataLocalManager;
import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.OpenWeather.CurrentWeatherResult;
import com.example.openweather.Model.OpenWeather.OpenWeather;
import com.example.openweather.Service.LocationInfo;

import java.util.List;

public class FragCurrentWeatherViewModel extends ViewModel {
        private MutableLiveData<String>  tvLocation;
        private MutableLiveData<String>  imgWeatherIcon;
        private MutableLiveData<String>  tvWeatherCondition;
        private MutableLiveData<String>  tvDescription;
        private MutableLiveData<String>  tvTemperature;
        private MutableLiveData<String>  tvFeelsLike;

        public FragCurrentWeatherViewModel() {
                imgWeatherIcon = new MutableLiveData<>();
                tvWeatherCondition = new MutableLiveData<>();
                tvDescription = new MutableLiveData<>();
                tvTemperature = new MutableLiveData<>();
                tvFeelsLike = new MutableLiveData<>();
        }

        public void getCurrentWeatherByName(String location){

                OpenWeather.currentWeatherByName(AppWeather.getContext(), location, new CurrentWeatherResult() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {
                                tvLocation.setValue(currentWeather.getName()+", " + currentWeather.getSys().getCountry());
                                imgWeatherIcon .setValue(currentWeather.getListWeather().get(0).getIcon());
                                tvWeatherCondition .setValue(currentWeather.getListWeather().get(0).getMain());
                                tvDescription .setValue(currentWeather.getListWeather().get(0).getDescription());
                                tvTemperature .setValue(String.valueOf((int)currentWeather.getMain().getTemp()));
                                tvFeelsLike .setValue(String.valueOf((int)currentWeather.getMain().getFeelsLike()));
                        }

                        @Override
                        public void onFailed() {

                        }
                });
        }

        void getCurrentWeatherByLocation(String latitude,String longitude){

                OpenWeather.currentWeatherByCoordinate(AppWeather.getContext(), latitude, longitude, new CurrentWeatherResult() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {
                                tvLocation.setValue(currentWeather.getName()+", " + currentWeather.getSys().getCountry());
                                imgWeatherIcon .setValue(currentWeather.getListWeather().get(0).getIcon());
                                tvWeatherCondition .setValue(currentWeather.getListWeather().get(0).getMain());
                                tvDescription .setValue(currentWeather.getListWeather().get(0).getDescription());
                                tvTemperature .setValue(String.valueOf((int)currentWeather.getMain().getTemp()));
                                tvFeelsLike .setValue(String.valueOf((int)currentWeather.getMain().getFeelsLike()));
                        }

                        @Override
                        public void onFailed() {

                        }
                });
        }


        public MutableLiveData<String> getTvLocation() {
                if(tvLocation == null){
                        tvLocation = new MutableLiveData<>();
                }
                return tvLocation;
        }

        public MutableLiveData<String> getImgWeatherIcon() {
                if(imgWeatherIcon == null){
                        imgWeatherIcon = new MutableLiveData<>();
                }
                return imgWeatherIcon;
        }

        public MutableLiveData<String> getTvWeatherCondition() {
                if(tvWeatherCondition == null){
                        tvWeatherCondition = new MutableLiveData<>();
                }
                return tvWeatherCondition;
        }

        public MutableLiveData<String> getTvDescription() {
                if(tvDescription == null){
                        tvDescription = new MutableLiveData<>();
                }
                return tvDescription;
        }

        public MutableLiveData<String> getTvTemperature() {
                if(tvTemperature == null){
                        tvTemperature = new MutableLiveData<>();
                }
                return tvTemperature;
        }

        public MutableLiveData<String> getTvFeelsLike() {
                if(tvFeelsLike == null){
                        tvFeelsLike = new MutableLiveData<>();
                }
                return tvFeelsLike;
        }



        //Init weather at first start application, default get location by Gps
        public void initLastestWeatherLocation(){
                String location = DataLocalManager.getInstance().getLastestLocation();
                if(!location.equals("")){
                        getCurrentWeatherByName(location);
                }else{
                        List<String> locationInfo = LocationInfo.getCurrentLocationInfo();
                        getCurrentWeatherByLocation(locationInfo.get(0),locationInfo.get(1));
                }
        }

        public void getCurrentWeatherByLocation(){
                List<String> locationInfo = LocationInfo.getCurrentLocationInfo();
                getCurrentWeatherByLocation(locationInfo.get(0),locationInfo.get(1));

        }


}

