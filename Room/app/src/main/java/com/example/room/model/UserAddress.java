package com.example.room.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UserAddress {
    @Embedded
    private User user;

    @Relation(parentColumn = "user_id",entityColumn = "userID")
    private Address address;

    public UserAddress(User user, Address address) {
        this.user = user;
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "user=" + user.toString() +
                ", address=" + address.toString() +
                '}';
    }
}
