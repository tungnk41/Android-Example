package com.example.recyclevmlive;

public class User {
    private int avatar;
    private String name;
    private String address;

    public User(int avatar, String name, String address) {
        this.avatar = avatar;
        this.name = name;
        this.address = address;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
