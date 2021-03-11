package com.example.recyclevmlive;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> listUserLiveData;
    private List<User> listUser;

    public UserViewModel() {
        listUserLiveData = new MutableLiveData<>();
        listUser = new ArrayList<>();
        initData();

    }

    private void initData(){
        listUser.add(new User(R.mipmap.ic_image,"User 1","Address 1"));
        listUser.add(new User(R.mipmap.ic_image,"User 2","Address 2"));
        listUser.add(new User(R.mipmap.ic_image,"User 3","Address 3"));

        listUserLiveData.setValue(listUser);
    }

    public MutableLiveData<List<User>> getListUserLiveData() {
        return listUserLiveData;
    }

    public void addUser(User user){
        listUser.add(user);
        listUserLiveData.setValue(listUser);
    }
}
