package com.example.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserEntity {
    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "USER_NAME")
    private String userName;

    @Generated(hash = 1766799416)
    public UserEntity(long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}
