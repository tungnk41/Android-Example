package com.example.workmanagerext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnStop;
    WorkRequest oneTimeRequest;
    WorkRequest periodicRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        initWorkRequest();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStart();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStop();
            }
        });
    }

    void initWorkRequest(){
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data data = new Data.Builder().putString("key","value").build();

        oneTimeRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                .setInputData(data)
                .setConstraints(builder.build())
                .addTag("Work_1")
                .build();

        periodicRequest = new PeriodicWorkRequest.Builder(BackgroundWorker.class,PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.SECONDS)
                .setInputData(data)
                .setConstraints(builder.build())
                .addTag("Work_2")
                .build();
    }


    void clickStart(){
        WorkManager.getInstance(this).enqueue(oneTimeRequest);
        WorkManager.getInstance(this).enqueue(periodicRequest);

        //chainning work
        //WorkManager.getInstance(this).beginWith(oneTimeRequest).then(oneTimeRequest2).enqueue()
        //WorkManager.getInstance(this).beginWith(Arrays.asList(oneTimeRequest1,oneTimeRequest2)).then(oneTimeRequest3).enqueue();
    }


    void clickStop(){
        //WorkManager.getInstance(this).cancelAllWorkByTag("Work_1");
        WorkManager.getInstance(this).cancelWorkById(oneTimeRequest.getId());
        WorkManager.getInstance(this).cancelWorkById(periodicRequest.getId());
    }
}