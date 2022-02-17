package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnStartService;

    private BoundService boundService;
    private boolean isBounded = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            boundService = binder.getService();
            isBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBounded = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btnStartService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind service
        Intent intent = new Intent(this,BoundService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //For StartedService
        Intent intent = new Intent(this,StartedService.class);
        stopService(intent);
        //For BoundService
        unbindService(connection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //For ForegroundService
        Intent fgIntent = new Intent(this,ForegroundService.class);
        stopService(fgIntent);
    }

    private void clickStart() {
        //For StartedService
//        Intent intent = new Intent(this,StartedService.class);
//        startService(intent);

        //For BoundService
        if(isBounded){
            //Call method from Bound service
            boundService.makeToast();
        }

        //For ForegroundService
//        Intent fgIntent = new Intent(this,ForegroundService.class);
//        fgIntent.putExtra("input","Detail Message from ForegoundService");
//        startForegroundService(fgIntent);

        //IntentService
        Intent intentServ = new Intent(this,IntentServiceEx.class);
        IntentServiceEx.enqueueWork(this,intentServ);
    }

}