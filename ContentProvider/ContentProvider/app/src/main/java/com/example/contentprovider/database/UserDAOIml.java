package com.example.contentprovider.database;

import android.database.Cursor;
import android.util.Log;

import com.example.contentprovider.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOIml implements UserDAO {

    @Override
    public void insertUser(User user) {
        UserDatabase.getInstance()._insertionEntity(user);
    }

    @Override
    public List<User> getListUser() {
        List<User> listUser = new ArrayList<>();
        Cursor cursor = UserDatabase.getInstance()._getAllEntity();
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId((int)cursor.getLong(cursor.getColumnIndex(UserDatabase.COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserDatabase.COLUMN_NAME)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(UserDatabase.COLUMN_ADDRESS)));
                listUser.add(user);
                Log.d("TAG", "testDB: username " + user.getName() + " address " + user.getAddress() + " id " + user.getId());
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listUser;
    }

    @Override
    public void deleteUser(User user) {
        UserDatabase.getInstance()._deleteEntity(user);
    }

    @Override
    public void deleteAll() {
        UserDatabase.getInstance()._dropTable();
    }

    @Override
    public boolean isUserExist(User user) {
        Cursor cursor = UserDatabase.getInstance()._getEntity(user);
        boolean isExist = (cursor.getCount() != 0);
        cursor.close();

        return isExist;
    }

    @Override
    public void updateUser(User user) {
        UserDatabase.getInstance()._updateEntity(user);
    }
}
