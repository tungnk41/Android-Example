package com.example.pushnotification;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    Button btnSendNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendNoti = findViewById(R.id.btnSendNoti);

        btnSendNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendNoti();
                sendCustomNoti();
            }
        });
    }

    private void sendNoti(){
        final int NOTIFICATION_ID = 1;
        String  title_noti = "News";
        String content_noti =
                "Tuy nhiên, nếu xem xét kỹ hơn thì thực tế, " +
                "những yếu tố chủ chốt giúp Biden tăng tốc quá trình sản xuất vaccine đã được thiết lập từ trước khi ông lên nắm quyền." +
                " Cả hai chính quyền đều xứng đáng được ghi nhận công lao, " +
                "mặc dù cả hai đều không muốn bên kia được công nhận, bình luận viên Sharon LaFraniere của NYTimes nhận xét.";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_image);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);

            Intent intent = new Intent(this,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID_1)
                    .setContentTitle(title_noti)
                    .setContentText(content_noti)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(content_noti))
                    //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .setSmallIcon(R.drawable.ic_noti)
                    .setLargeIcon(bitmap)
                    .setColor(getResources().getColor(R.color.design_default_color_primary,null))
                    .setSound(sound)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true) // When click notification, it will be disappear
                    .build();

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID,notification);
        }
    }

    private void sendCustomNoti() {
        final int NOTIFICATION_ID = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_image);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_noti);

            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID_1)
                    //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .setSmallIcon(R.drawable.ic_noti)
                    .setLargeIcon(bitmap)
                    .setColor(getResources().getColor(R.color.design_default_color_primary, null))
                    .setSound(sound)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true) // When click notification, it will be disappear
                    .setContent(contentView)
                    .build();

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        }
    }
}