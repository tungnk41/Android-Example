package com.example.room.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.room.model.Address;
import com.example.room.model.User;
import com.example.room.model.UserAddress;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Insert
    void insertAddress(Address address);

    @Update
    void updateUser(User user);

    @Update
    void updateAddress(Address address);

    @Transaction
    @Query("SELECT * FROM user")
    List<UserAddress> getAllUser();

    @Transaction
    @Query("SELECT * FROM  user WHERE user_id = :userId")
    UserAddress getUser(int userId);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Transaction
    @Query("SELECT * FROM user WHERE userName LIKE '%' || :name || '%'")
    List<UserAddress> searchUser(String name);
}
