package com.example.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "address",
foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "user_id",childColumns = "userID",onDelete = ForeignKey.CASCADE)})
public class Address {

    public Address(String address) {
        this.address = address;
    }

    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String address;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "userID=" + userID +
                ", address='" + address + '\'' +
                '}';
    }
}
