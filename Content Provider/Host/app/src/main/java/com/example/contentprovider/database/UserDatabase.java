package com.example.contentprovider.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contentprovider.App;
import com.example.contentprovider.model.User;

public class UserDatabase {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private static UserDatabase instance;
    private static UserSQLiteHelper dbHelper;
    public UserDAO userDAO;

    public static class UserSQLiteHelper extends SQLiteOpenHelper {
        public UserSQLiteHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_ADDRESS + " TEXT )"
            );
        }
        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(database);
        }
    }

    private UserDatabase(Context context) {
        dbHelper = new UserSQLiteHelper(context);
        userDAO = new UserDAOIml();
    }

    public static synchronized UserDatabase getInstance() {
        if(instance == null){
            instance = new UserDatabase(App.getContext());
        }
        return instance;
    }

    public SQLiteDatabase getRawDatabase(){
        return dbHelper.getWritableDatabase();
    }
    public long _insertionEntity(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,user.getName());
        contentValues.put(COLUMN_ADDRESS,user.getAddress());
        return database.insert(TABLE_NAME,null,contentValues);

    }

    public Cursor _getEntity(User user){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return  database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=? " + " AND " + COLUMN_ADDRESS + "=?",
                new String[]{user.getName(),user.getAddress()});
    }

    public void _updateEntity(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + "=? , " + COLUMN_ADDRESS + "=? " + "  WHERE " + COLUMN_ID + "=?",
                new String[]{user.getName(),user.getAddress(),String.valueOf(user.getId())});
        cursor.close();
    }

    public void _deleteEntity(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=?" + " AND " + COLUMN_ADDRESS + "=?",
                new String[]{user.getName(),user.getAddress()});
        cursor.close();
    }

    public Cursor _getAllEntity(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME,null);
    }

    public void _dropTable(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("DROP TABLE " + TABLE_NAME);
    }

    public void _deleteTable(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void close(){
        dbHelper.close();
    }

}
