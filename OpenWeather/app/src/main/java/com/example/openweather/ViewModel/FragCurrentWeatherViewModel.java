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

        public void initCurrentWeatherByName(String city){

                OpenWeather.currentWeatherByName(AppWeather.getContext(), city, new CurrentWeatherResult() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {
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

        void initCurrentWeatherByLocation(String latitude,String longitude){

                OpenWeather.currentWeatherByLocation(AppWeather.getContext(), latitude, longitude, new CurrentWeatherResult() {
                        @Override
                        public void onSuccess(CurrentWeather currentWeather) {
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



        public void initLastestWeatherLocation(){
                String city = DataLocalManager.getInstance().getLastestLocation();
                if(!city.equals("")){
                        initCurrentWeatherByName(city);
                }else{
                        List<String> locationInfo = LocationInfo.getCurrentLocationInfo();
                        initCurrentWeatherByLocation(locationInfo.get(0),locationInfo.get(1));
                        DataLocalManager.getInstance().saveLocation(city);
                }
        }


}

