package com.example.contentprovider.database;

import com.example.contentprovider.model.User;

import java.util.List;

public interface UserDAO {
    void insertUser(User user);

    //Update name,address base on ID
    void updateUser(User user);

    boolean isUserExist(User user);

    void deleteUser(User user);

    List<User> getListUser();

    void deleteAll();
}
