package com.example.openweather.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.openweather.R;
import com.example.openweather.Utils.WeatherUtil;
import com.example.openweather.View.MainActivity;
import com.example.openweather.ViewModel.FragCurrentWeatherViewModel;
import com.example.openweather.databinding.FragmentCurrentWeatherBinding;


public class FragCurrentWeather extends Fragment {

    TextView tvLocation;
    private FragCurrentWeatherViewModel fragViewModel;
    private FragmentCurrentWeatherBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_current_weather_fragment, container, false);
        binding = FragmentCurrentWeatherBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvLocation = getActivity().findViewById(R.id.tvLocation);

        fragViewModel = new ViewModelProvider(this).get(FragCurrentWeatherViewModel.class);
        refreshCurrentWeather();
        createObserverToModel();
    }


    void createObserverToModel(){

        final Observer<String> location = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvLocation.setText(s);
            }
        };

        final Observer<String> imageWeatherObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                fetchImage(s);
            }
        };

        final Observer<String> weatherCondition = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvWeatherCondition.setText(s);
            }
        };

        final Observer<String> weatherDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvDescription.setText(s);
            }
        };

        final Observer<Integer> temperature = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String temperature = s+getStringResource(R.string.str_weather_temperature_unit_c);
                binding.tvTemperature.setText(temperature);
            }
        };

        final Observer<Integer> temperatureFeelsLike = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String feelsLike = getStringResource(R.string.str_weather_feels_like_temperature) + " " + s +
                        getStringResource(R.string.str_weather_temperature_unit_c);
                binding.tvFeelsLike.setText(feelsLike);
            }
        };

        /******************************************************************************************/

        final Observer<Double> windDetailSpeed = new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                String windDetailSpeed = getStringResource(R.string.str_weather_detail_wind_speed) + " " + s +
                        getStringResource(R.string.str_weather_detail_wind_speed_unit);
                binding.tvWeatherDetail.tvWindDetailSpeed.setText(windDetailSpeed);
            }
        };

        final Observer<Integer> humidityDetail = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String humidity = getStringResource(R.string.str_weather_detail_humidity) + " " + s +
                        getStringResource(R.string.str_weather_detail_humidity_unit);
                binding.tvWeatherDetail.tvHumidityDetail.setText(humidity);
            }
        };

        final Observer<Integer> clouds = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String clouds = getStringResource(R.string.str_weather_detail_clouds) + " " + s +
                        getStringResource(R.string.str_weather_detail_clouds_unit);
                binding.tvWeatherDetail.tvClouds.setText(clouds);
            }
        };

        final Observer<Integer> pressureDetail = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String pressure = getStringResource(R.string.str_weather_detail_pressure) + " " + s +
                        getStringResource(R.string.str_weather_detail_pressure_unit);
                binding.tvWeatherDetail.tvPressureDetail.setText(pressure);
            }
        };

        final Observer<Integer> sunRise = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String sunrise = getStringResource(R.string.str_weather_detail_sunrise) + " " + WeatherUtil.getInstance().timeFormatter(s);
                binding.tvWeatherDetail.tvSunRise.setText(sunrise);
            }
        };

        final Observer<Integer> sunSet = new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                String sunset = getStringResource(R.string.str_weather_detail_sunset) + " " + WeatherUtil.getInstance().timeFormatter(s);
                binding.tvWeatherDetail.tvSunSet.setText(sunset);
            }
        };




        fragViewModel.getTvLocation().observe(this,location);
        fragViewModel.getImgWeatherIcon().observe(this,imageWeatherObserver);
        fragViewModel.getTvWeatherCondition().observe(this,weatherCondition);
        fragViewModel.getTvDescription().observe(this,weatherDescription);
        fragViewModel.getTvTemperature().observe(this,temperature);
        fragViewModel.getTvFeelsLike().observe(this,temperatureFeelsLike);

        fragViewModel.getTvWindDetailSpeed().observe(this,windDetailSpeed);
        fragViewModel.getTvHumidityDetail().observe(this,humidityDetail);
        fragViewModel.getTvClouds().observe(this,clouds);
        fragViewModel.getTvPressureDetail().observe(this,pressureDetail);
        fragViewModel.getTvSunRise().observe(this,sunRise);
        fragViewModel.getTvSunSet().observe(this,sunSet);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getStringResource(int id){
        return getResources().getString(id);
    }

    private void fetchImage(String image){
        final String url = "https://openweathermap.org/img/wn/" + image + "@2x.png";
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(binding.imgWeatherIcon);
    }

    public void fetchCurrentWeatherByLocation(){
        fragViewModel.fetchCurrentWeatherByCoordinate(getContext());
    }

    public void refreshCurrentWeather(){
        fragViewModel.refreshCurrentWeather(getContext());
    }
}