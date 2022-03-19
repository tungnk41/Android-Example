package com.example.pushnotification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class App extends Application {
    public static String CHANNEL_ID_1 = "01";
    public static String CHANNEL_ID_2 = "02";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Uri soundDefault = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel notiChannel1 = new NotificationChannel(CHANNEL_ID_1,"Notification",NotificationManager.IMPORTANCE_HIGH);
            notiChannel1.setDescription("description");
            notiChannel1.setSound(sound,attributes);
            NotificationChannel notiChannel2 = new NotificationChannel(CHANNEL_ID_2,"Notification",NotificationManager.IMPORTANCE_HIGH);
            notiChannel2.setDescription("description");
            notiChannel2.setSound(soundDefault,attributes);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notiChannel1);
            notificationManager.createNotificationChannel(notiChannel2);
        }
    }
}
