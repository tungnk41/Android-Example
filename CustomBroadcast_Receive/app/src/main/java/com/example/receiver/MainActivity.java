package com.example.receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String BROADCAST_CUSTOM_STRING_ACTION = "broadcast.custom.string.action";
    private static final String BROADCAST_CUSTOM_OBJECT_ACTION = "broadcast.custom.object.action";
    private static final String DATA_STRING = "data.string";
    private static final String DATA_OBJECT = "data.object";
    TextView tvReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvReceiver = findViewById(R.id.tvReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_CUSTOM_STRING_ACTION)){
                String strData = intent.getStringExtra(DATA_STRING);
                tvReceiver.setText(strData);
            }
            if(intent.getAction().equals(BROADCAST_CUSTOM_OBJECT_ACTION)){
                String strJsonObject = intent.getStringExtra(DATA_OBJECT);
                Gson gson = new Gson();
                User user = gson.fromJson(strJsonObject,User.class);
                tvReceiver.setText(user.toString());
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_CUSTOM_STRING_ACTION);
        intentFilter.addAction(BROADCAST_CUSTOM_OBJECT_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}