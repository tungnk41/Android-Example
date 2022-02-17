package com.example.room.database.dummyData;

import com.example.room.database.UserDatabase;
import com.example.room.model.Address;
import com.example.room.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProvider {
    public DatabaseProvider() {

    }

    public List<User> initUserList(){
        List<User> listUser = new ArrayList<>();
        listUser.add(new User("Tung",19));

        return listUser;
    }

    public List<Address> initAddressList(){
        List<Address> listAddress = new ArrayList<>();

        listAddress.add(new Address("HaNoi"));
        return listAddress;
    }
}
