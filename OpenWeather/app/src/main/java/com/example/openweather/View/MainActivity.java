package com.example.openweather.View;

import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;


import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.openweather.R;
import com.example.openweather.View.Activity.OptionActivity;
import com.example.openweather.View.Activity.SearchActivity;
import com.example.openweather.View.Fragment.FragCurrentWeather;


public class MainActivity extends AppCompatActivity {
    Button btnSearch;
    ImageView imgMenu;
    ImageView imgLocation;
    SwipeRefreshLayout swipeRefresh;
    FragmentManager fragmentManager;
    FragCurrentWeather fragCurrentWeather;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSearchClicked();
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgMenuClicked();
            }
        });
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgLocationClicked();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefreshActived();
            }
        });
    }

    void initView(){
        btnSearch = findViewById(R.id.btnSearch);
        imgMenu = findViewById(R.id.imgMenu);
        imgLocation = findViewById(R.id.imgLocation);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        fragmentManager = getSupportFragmentManager();
        fragCurrentWeather = new FragCurrentWeather();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragCurrentWeather,fragCurrentWeather);
        transaction.commit();

    }

    void onBtnSearchClicked(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    void onImgMenuClicked(){
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }

    void onImgLocationClicked(){
        fragCurrentWeather.fetchCurrentWeatherByLocation();
    }

    void onSwipeRefreshActived(){
        //To do Job
        fragCurrentWeather.refreshCurrentWeather();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Only for running animation
                swipeRefresh.setRefreshing(false);
            }
        },3000);
    }





//    void getForecastWeatherByName(String city) {
//
//        OpenWeather.forecastWeatherByName(this, city, new ForecastWeatherResult() {
//            @Override
//            public void onSuccess(Forecast forecastWeather) {
//                tvData.setText(forecastWeather.toString());
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
//    }
//

}