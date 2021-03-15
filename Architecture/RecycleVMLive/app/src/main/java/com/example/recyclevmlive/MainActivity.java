package com.example.recyclevmlive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListUser;
    private Button btnAddUser;
    private UserAdapter userAdapter;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddUser = findViewById(R.id.btnAddUser);
        rvListUser = findViewById(R.id.rvListUser);
        initViewModel();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

    }

    private void initViewModel(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListUser.setLayoutManager(linearLayoutManager);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getListUserLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter = new UserAdapter(users);
                rvListUser.setAdapter(userAdapter);

            }
        });
    }

    private void addUser(){
        User user = new User(R.mipmap.ic_image,"User ext ","Address ext");
        userViewModel.addUser(user);

    }
}