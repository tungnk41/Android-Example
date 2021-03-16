package com.example.openweather.View.Fragment;

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

import com.bumptech.glide.Glide;
import com.example.openweather.ViewModel.FragCurrentWeatherViewModel;
import com.example.openweather.databinding.FragmentCurrentWeatherBinding;


public class FragCurrentWeather extends Fragment {


    private FragCurrentWeatherViewModel fragViewModel;
    public static FragCurrentWeather newInstance() {
        return new FragCurrentWeather();
    }

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
        fragViewModel = new ViewModelProvider(this).get(FragCurrentWeatherViewModel.class);
        fragViewModel.initLastestWeatherLocation();
        createObserverToModel();
    }

    void createObserverToModel(){
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

        final Observer<String> temperature = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvTemperature.setText(s + "\u2103"); //\u2109
            }
        };

        final Observer<String> temperatureFeelsLike = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvFeelsLike.setText("Feels like " + s + "\u2103");
            }
        };

        fragViewModel.getImgWeatherIcon().observe(this,imageWeatherObserver);
        fragViewModel.getTvWeatherCondition().observe(this,weatherCondition);
        fragViewModel.getTvDescription().observe(this,weatherDescription);
        fragViewModel.getTvTemperature().observe(this,temperature);
        fragViewModel.getTvFeelsLike().observe(this,temperatureFeelsLike);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void fetchImage(String image){
        final String url = "https://openweathermap.org/img/wn/" + image + "@2x.png";
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(binding.imgWeatherIcon);
    }
}