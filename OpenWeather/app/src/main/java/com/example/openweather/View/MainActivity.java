package com.example.openweather.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.openweather.R;
import com.example.openweather.View.Fragment.FragCurrentWeather;



public class MainActivity extends AppCompatActivity {
    Button btnSearch;
    ImageView imgMenu;

    FragCurrentWeather fragCurrentWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Must check permission onCreate function
        requestPermission();
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
    }

    void initView(){
        btnSearch = findViewById(R.id.btnSearch);
        imgMenu = findViewById(R.id.imgMenu);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragCurrentWeather fragCurrentWeather = new FragCurrentWeather();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragCurrentWeather,fragCurrentWeather);
        transaction.commit();
    }

    void onBtnSearchClicked(){
        Toast.makeText(MainActivity.this, "Seach", Toast.LENGTH_SHORT).show();
    }



    void onImgMenuClicked(){
        Toast.makeText(MainActivity.this, "Menu", Toast.LENGTH_SHORT).show();
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




    void requestPermission(){
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}