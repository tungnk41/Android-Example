package com.example.room.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room.R;
import com.example.room.database.UserDatabase;
import com.example.room.database.dummyData.DatabaseProvider;
import com.example.room.databinding.ActivityMainBinding;
import com.example.room.model.Address;
import com.example.room.model.User;
import com.example.room.model.UserAddress;
import com.example.room.ulti.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_UPDATE_USER = 1;
    private ActivityMainBinding binding;

    private UserAdapter userAdapter;
    private List<UserAddress> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
        loadData();

        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        binding.tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllUser();
            }
        });

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    handleSearchUser();
                }
                return false;
            }
        });

    }

    private void initView(){

        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(UserAddress user) {
                clickUpdateUser(user.getUser().getId());
            }

            @Override
            public void deleteUser(UserAddress user) {
                clickDeleteUser(user.getUser());
            }
        });
        listUser = new ArrayList<>();
        userAdapter.setData(listUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvUser.setLayoutManager(linearLayoutManager);
        binding.rvUser.setAdapter(userAdapter);
    }

    private void initData(){
        DatabaseProvider dataProvider = new DatabaseProvider();
        for(User user : dataProvider.initUserList()){
            UserDatabase.getInstance(this).userDAO().insertUser(user);
        }
        for(Address address : dataProvider.initAddressList()){
            UserDatabase.getInstance(this).userDAO().insertAddress(address);
        }
    }

    private void loadData(){


        listUser = UserDatabase.getInstance(this).userDAO().getAllUser();
        userAdapter.setData(listUser);
    }

    private void addUser() {
        String strUserName = binding.edtUserName.getText().toString().trim();
        String strUserAge = binding.edtUserAge.getText().toString().trim();
        String strAddress = binding.edtAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress) || TextUtils.isEmpty(strUserAge)){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(strUserName,Integer.valueOf(strUserAge));
        Address address = new Address(strAddress);

        UserDatabase.getInstance(this).userDAO().insertUser(user);
        UserDatabase.getInstance(this).userDAO().insertAddress(address);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        binding.edtAddress.setText("");
        binding.edtUserName.setText("");
        binding.edtAddress.setText("");
        loadData();
        Utils.getInstance().hideSoftKeyboard(this);

    }


    private void clickUpdateUser(int userID) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("user_id", userID);
        startActivityForResult(intent,REQUEST_UPDATE_USER);
    }

    private void clickDeleteUser(User user){
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteUser(user);
                        Toast.makeText(MainActivity.this, "Delete user successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteAllUser(){
        new AlertDialog.Builder(this)
                .setTitle("Delete all user")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteAllUser();
                        Toast.makeText(MainActivity.this, "Delete user successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleSearchUser(){
        String strSearch = binding.edtSearch.getText().toString().trim();
        listUser.clear();
        listUser = UserDatabase.getInstance(this).userDAO().searchUser(strSearch);
        userAdapter.setData(listUser);
        Utils.getInstance().hideSoftKeyboard(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_USER && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }
}