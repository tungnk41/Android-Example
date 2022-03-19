package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundService extends Service {

    private final IBinder binder =  new LocalBinder();
    public class LocalBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Method for client
    public void makeToast(){
        Toast.makeText(this, "Make Toast from Bound Service", Toast.LENGTH_SHORT).show();
    }

    public List<String> getListString(Integer a) {
        Log.d("TAG", "getListString: " + a);
        return Arrays.asList("Text 1", "Text 2");
    }
}
