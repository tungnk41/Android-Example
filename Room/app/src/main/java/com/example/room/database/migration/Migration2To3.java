package com.example.room.database.migration;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration2To3 extends Migration {

    public Migration2To3() {
        super(2, 3);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL(
                "CREATE TABLE address (userID INTEGER NOT NULL, " +
                        "address TEXT NOT NULL, " +
                        "PRIMARY KEY (userID))"
        );
    }
}