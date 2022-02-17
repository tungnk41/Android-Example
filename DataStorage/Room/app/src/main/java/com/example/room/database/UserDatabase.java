package com.example.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.room.database.migration.Migration1To2;
import com.example.room.database.migration.Migration2To3;
import com.example.room.model.Address;
import com.example.room.model.User;


/*
* Add 1 column : ALTER TABLE ten_bang ADD COLUMN ten_column datatype(DATATYPE)
* Add n column : ALTER TABLE ten_bang ADD ten_column1 datatype_1 , ten_colum2 datatype2, ...
* Change datatype column : ALTER TABLE ten_bang ALTER COLUMN ten_column datatype(DATATYPE)
* Delete column : ALTER TABLE ten_bang DROP COLUMN ten_column datatype
* */
@Database(entities = {User.class, Address.class}, version = 3,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static Migration migration_from_1_to_2 = new Migration1To2();
    private static Migration migration_from_2_to_3 = new Migration2To3();

    private static final String DATABASE_NAME = "user.db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,UserDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(migration_from_1_to_2,migration_from_2_to_3)
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
