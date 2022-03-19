package com.example.room.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room.R;
import com.example.room.database.UserDatabase;
import com.example.room.databinding.ActivityUpdateBinding;
import com.example.room.model.Address;
import com.example.room.model.User;
import com.example.room.model.UserAddress;

public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    private User user;
    private Address address;

    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();

        binding.btnUpdateAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    private void initData(){
        userID = (int)getIntent().getExtras().get("user_id");
        Log.d("TAG", "initData: " + userID);
        UserAddress userAddress = UserDatabase.getInstance(this).userDAO().getUser(userID);
        user = userAddress.getUser();
        address = userAddress.getAddress();
            binding.edtUpdateUserName.setText(user.getUserName());
            binding.edtUpdateUserAge.setText(String.valueOf(user.getAge()));
            binding.edtUpdateAddress.setText(address.getAddress());
    }

    private void updateUser(){
        String strUserName = binding.edtUpdateUserName.getText().toString().trim();
        String strUserAge = binding.edtUpdateUserAge.getText().toString().trim();
        String strAddress = binding.edtUpdateAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress) || TextUtils.isEmpty(strUserAge)){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setUserName(strUserName);
        user.setAge(Integer.valueOf(strUserAge));
        address.setAddress(strAddress);
        UserDatabase.getInstance(this).userDAO().updateUser(user);
        UserDatabase.getInstance(this).userDAO().updateAddress(address);

        Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();

    }
}