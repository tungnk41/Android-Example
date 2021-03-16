package com.example.openweather.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.openweather.DataLocal.DataLocalManager;
import com.example.openweather.Model.CurrentWeather.CurrentWeather;
import com.example.openweather.Model.Forecast.Forecast;
import com.example.openweather.Model.OpenWeather.ForecastWeatherResult;
import com.example.openweather.Model.OpenWeather.OpenWeather;
import com.example.openweather.Model.OpenWeather.CurrentWeatherResult;
import com.example.openweather.R;
import com.example.openweather.Service.GpsTracker;
import com.example.openweather.Service.WeatherService;
import com.example.openweather.View.Fragment.FragCurrentWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnGetData;
    FragCurrentWeather fragCurrentWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = findViewById(R.id.btnGetData);

        //Must check permission onCreate function
        requestPermission();

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragCurrentWeather fragCurrentWeather = new FragCurrentWeather();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragCurrentWeather,fragCurrentWeather);
        transaction.commit();
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