package com.example.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.room.User;


/*
* Add 1 column : ALTER TABLE ten_bang ADD COLUMN ten_column datatype(DATATYPE)
* Add n column : ALTER TABLE ten_bang ADD ten_column1 datatype_1 , ten_colum2 datatype2, ...
* Change datatype column : ALTER TABLE ten_bang ALTER COLUMN ten_column datatype(DATATYPE)
* Delete column : ALTER TABLE ten_bang DROP COLUMN ten_column datatype
* */
@Database(entities = {User.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    static Migration migration_from_1_to_2 = new Migration(1,2) { //Add new year column for user
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN year INTEGER"); //INTEGER NOT NULL DEFAULT 0
        }
    };

    private static final String DATABASE_NAME = "user.db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,UserDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(migration_from_1_to_2)
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
