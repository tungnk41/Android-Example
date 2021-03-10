package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room.database.UserDatabase;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    private EditText edtUpdateUserName;
    private EditText edtUpdateAddress;
    private Button btnUpdateAddUser;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
        initData();

        btnUpdateAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    private void initView(){
        edtUpdateUserName = findViewById(R.id.edtUpdateUserName);
        edtUpdateAddress = findViewById(R.id.edtUpdateAddress);
        btnUpdateAddUser = findViewById(R.id.btnUpdateAddUser);
    }

    private void initData(){
        user = (User) getIntent().getExtras().get("user");
        if(user != null){
            edtUpdateUserName.setText(user.getUserName());
            edtUpdateAddress.setText(user.getAddress());
        }
    }

    private void updateUser(){
        String strUserName = edtUpdateUserName.getText().toString().trim();
        String strAddress = edtUpdateAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setUserName(strUserName);
        user.setAddress(strAddress);

        UserDatabase.getInstance(this).userDAO().updateUser(user);
        Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();

    }
}