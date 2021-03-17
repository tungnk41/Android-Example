package com.example.openweather.ViewModel;

import android.content.Context;
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
        private MutableLiveData<Integer>  tvTemperature;
        private MutableLiveData<Integer>  tvFeelsLike;

        private MutableLiveData<Double>  tvWindDetailSpeed;
        private MutableLiveData<Integer>  tvHumidityDetail;
        private MutableLiveData<Integer>  tvClouds;
        private MutableLiveData<Integer>  tvPressureDetail;
        private MutableLiveData<Integer>  tvSunRise;
        private MutableLiveData<Integer>  tvSunSet;

        public FragCurrentWeatherViewModel() {
                imgWeatherIcon = new MutableLiveData<>();
                tvWeatherCondition = new MutableLiveData<>();
                tvDescription = new MutableLiveData<>();
                tvTemperature = new MutableLiveData<>();
                tvFeelsLike = new MutableLiveData<>();

                tvWindDetailSpeed = new MutableLiveData<>();
                tvHumidityDetail = new MutableLiveData<>();
                tvClouds = new MutableLiveData<>();
                tvPressureDetail = new MutableLiveData<>();
                tvSunRise = new MutableLiveData<>();
                tvSunSet = new MutableLiveData<>();
        }

        public void getCurrentWeatherByName(String location){

                OpenWeather.currentWeatherByName(AppWeather.getContext(), location, new CurrentWeatherResult() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {
                                tvLocation.setValue(currentWeather.getName()+", " + currentWeather.getSys().getCountry());
                                imgWeatherIcon .setValue(currentWeather.getListWeather().get(0).getIcon());
                                tvWeatherCondition .setValue(currentWeather.getListWeather().get(0).getMain());
                                tvDescription .setValue(currentWeather.getListWeather().get(0).getDescription());
                                tvTemperature .setValue((int)currentWeather.getMain().getTemp());
                                tvFeelsLike .setValue((int)currentWeather.getMain().getFeelsLike());

                                tvWindDetailSpeed.setValue(currentWeather.getWind().getSpeed());
                                tvHumidityDetail .setValue(currentWeather.getMain().getHumidity());
                                tvClouds .setValue(currentWeather.getClouds().getAll());
                                tvPressureDetail .setValue(currentWeather.getMain().getPressure());
                                tvSunRise .setValue(currentWeather.getSys().getSunrise());
                                tvSunSet .setValue(currentWeather.getSys().getSunset());
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
                                tvTemperature .setValue((int)currentWeather.getMain().getTemp());
                                tvFeelsLike .setValue((int)currentWeather.getMain().getFeelsLike());

                                tvWindDetailSpeed.setValue(currentWeather.getWind().getSpeed());
                                tvHumidityDetail .setValue(currentWeather.getMain().getHumidity());
                                tvClouds.setValue(currentWeather.getClouds().getAll());
                                tvPressureDetail .setValue(currentWeather.getMain().getPressure());
                                tvSunRise .setValue(currentWeather.getSys().getSunrise());
                                tvSunSet .setValue(currentWeather.getSys().getSunset());
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

        public MutableLiveData<Integer> getTvTemperature() {
                if(tvTemperature == null){
                        tvTemperature = new MutableLiveData<>();
                }
                return tvTemperature;
        }

        public MutableLiveData<Integer> getTvFeelsLike() {
                if(tvFeelsLike == null){
                        tvFeelsLike = new MutableLiveData<>();
                }
                return tvFeelsLike;
        }


        /***************************************************************/

        public MutableLiveData<Double> getTvWindDetailSpeed() {
                if(tvWindDetailSpeed == null){
                        tvWindDetailSpeed = new MutableLiveData<>();
                }
                return tvWindDetailSpeed;
        }

        public MutableLiveData<Integer> getTvHumidityDetail() {
                if(tvHumidityDetail == null){
                        tvHumidityDetail = new MutableLiveData<>();
                }
                return tvHumidityDetail;
        }

        public MutableLiveData<Integer> getTvClouds() {
                if(tvClouds == null){
                        tvClouds = new MutableLiveData<>();
                }
                return tvClouds;
        }

        public MutableLiveData<Integer> getTvPressureDetail() {
                if(tvPressureDetail == null){
                        tvPressureDetail = new MutableLiveData<>();
                }
                return tvPressureDetail;
        }

        public MutableLiveData<Integer> getTvSunRise() {
                if(tvSunRise == null){
                        tvSunRise = new MutableLiveData<>();
                }
                return tvSunRise;
        }

        public MutableLiveData<Integer> getTvSunSet() {
                if(tvSunSet == null){
                        tvSunSet = new MutableLiveData<>();
                }
                return tvSunSet;
        }


        //Init weather at first start application, default get location by Gps
        public void initLastestWeatherLocation(Context context){
                String location = DataLocalManager.getInstance().getLastestLocation();
                if(!location.equals("")){
                        getCurrentWeatherByName(location);
                }else{
                        List<String> locationInfo = LocationInfo.getCurrentLocationInfo(context);
                        getCurrentWeatherByLocation(locationInfo.get(0),locationInfo.get(1));
                }
        }

        public void fetchCurrentWeatherByLocation(Context context){
                List<String> locationInfo = LocationInfo.getCurrentLocationInfo(context);
                getCurrentWeatherByLocation(locationInfo.get(0),locationInfo.get(1));

        }


}

