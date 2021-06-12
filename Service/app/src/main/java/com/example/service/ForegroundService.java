package com.example.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.service.App.CHANNEL_ID;

public class ForegroundService extends Service {
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private RemoteViews remoteView;
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        initNoti();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "ForegroundService started", Toast.LENGTH_SHORT).show();

        startFG(intent);
        //setNotiRemoteView("");
        startValueNotiFromThread();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initNoti(){
        notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        remoteView = new RemoteViews(getPackageName(), R.layout.custom_layout);
        notificationBuilder
                .setContentTitle("Foreground Service")
                //.setContentText(input)
                //.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        //.setContent(remoteView);
    }

    private void startFG(Intent intent){
        String input = intent.getStringExtra("input");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        notificationBuilder
                .setContentText(input)
                .setContentIntent(pendingIntent);

        startForeground(1, notificationBuilder.build());
    }

    private void setNotiRemoteView(String msg){
        Log.d("TAG", "updateNotiRemoteView: " + msg);
        remoteView.setTextViewText(R.id.txtCount,msg);

        notificationBuilder
                .setContent(remoteView);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void setNotiContentText(String msg){
        notificationBuilder
                .setContentText(msg);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void startValueNotiFromThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<10; i++){
                    try {
                        Thread.sleep(1000);
                        count++;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setNotiContentText(String.valueOf(count));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }


}
