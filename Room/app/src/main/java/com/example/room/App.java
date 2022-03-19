package com.example.room;

import android.app.Application;

import androidx.room.Room;

import com.example.room.database.UserDatabase;

public class App extends Application {
//    public static UserDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();

//        database = Room.databaseBuilder(this,UserDatabase.class,"USER_DATABASE")
//                .addMigrations()
//                .build();
    }
}
