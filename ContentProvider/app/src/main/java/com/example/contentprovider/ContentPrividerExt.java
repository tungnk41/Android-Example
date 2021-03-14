package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contentprovider.database.UserDatabase;
import com.example.contentprovider.model.User;

import java.util.HashMap;

public class ContentPrividerExt extends ContentProvider {
    static final String AUTHORITY = "com.example.contentprovider.provider";
    static final String TABLE_NAME = UserDatabase.TABLE_NAME;
    static final String URI = "content://" + AUTHORITY + "/" + TABLE_NAME;
    static final Uri CONTENT_URI = Uri.parse(URI);
    SQLiteDatabase database;

    private static HashMap<String, String> projectMap; //For renaming column from query command to Database

    static final int URI_MULTIPLE_ROW_CODE = 1;
    static final int URI_SINGLE_ROW_CODE = 2;

    //Column in database
    static final String ID = UserDatabase.COLUMN_ID;
    static final String NAME = UserDatabase.COLUMN_NAME;
    static final String ADDRESS = UserDatabase.COLUMN_ADDRESS;

    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        /*
         * To access whole table
         */
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, URI_MULTIPLE_ROW_CODE);

        /*
         * To access a particular row of table
         */
        uriMatcher.addURI(AUTHORITY, TABLE_NAME +"/#", URI_SINGLE_ROW_CODE);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = UserDatabase.getInstance().getRawDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)){
            case URI_MULTIPLE_ROW_CODE:
                queryBuilder.setProjectionMap(projectMap);
                break;
            case URI_SINGLE_ROW_CODE:
                queryBuilder.appendWhere(ID + "=" + uri.getPathSegments().get(1));
                break;
            default: throw new IllegalArgumentException ("Unknown URI: " + uri);
        }

        if(TextUtils.isEmpty(sortOrder)){
            sortOrder = ID;
        }

        Cursor cursor = queryBuilder.query(database,projection,selection,selectionArgs,null,null,sortOrder);
        if (getContext() != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type = "";
        switch (uriMatcher.match(uri)){
            case URI_MULTIPLE_ROW_CODE:
                type = "vnd.android.cursor.dir/" + AUTHORITY + "/" + TABLE_NAME;
                break;
            case URI_SINGLE_ROW_CODE:
                type = "vnd.android.cursor.item/" + AUTHORITY + "/" + TABLE_NAME;;
                break;
            default: throw new IllegalArgumentException ("Unknown URI: " + uri);
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = UserDatabase.getInstance().getRawDatabase();
        long rowID = 0;
        switch (uriMatcher.match(uri)){
            case URI_MULTIPLE_ROW_CODE:
                break;
            case URI_SINGLE_ROW_CODE:
                rowID = database.insert(TABLE_NAME,null,values);
                break;
            default: throw new IllegalArgumentException ("Unknown URI: " + uri);
        }
        if(getContext() != null){
            if(rowID != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, rowID);
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = UserDatabase.getInstance().getRawDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)){
            case URI_MULTIPLE_ROW_CODE:
                count = database.delete(TABLE_NAME,selection,selectionArgs);
                break;
            case URI_SINGLE_ROW_CODE:
                String id = uri.getPathSegments().get(1);
                count = database.delete(TABLE_NAME, ID + "=" +id +
                                        (!TextUtils.isEmpty(selection) ?  " AND ( " + selection + " )" : ""),
                                        selectionArgs);
                break;
            default: throw new IllegalArgumentException ("Unknown URI: " + uri);
        }
        if(getContext() != null){
            if(count != 0){
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = UserDatabase.getInstance().getRawDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)){
            case URI_MULTIPLE_ROW_CODE:
                count = database.update(TABLE_NAME,values,selection,selectionArgs);
                break;
            case URI_SINGLE_ROW_CODE:
                String id = uri.getPathSegments().get(1);
                count = database.update(TABLE_NAME,values, ID + "="+ id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""),
                                selectionArgs);
                break;
            default: throw new IllegalArgumentException ("Unknown URI: " + uri);
        }
        if(getContext() != null){
            if(count != 0){
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return count;
    }


    private User convertToUser(ContentValues contentValues){
        User user = new User();
        String COLUMN_NAME = UserDatabase.COLUMN_NAME;
        String COLUMN_ADDRESS = UserDatabase.COLUMN_ADDRESS;
        if (contentValues.containsKey(COLUMN_NAME)) {
            user.setName(contentValues.getAsString(COLUMN_NAME));
        } if (contentValues.containsKey(COLUMN_ADDRESS)) {
            user.setAddress(contentValues.getAsString(COLUMN_ADDRESS));
        }
        return user;
    }
}
