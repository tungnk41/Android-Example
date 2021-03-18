package com.example.sharepreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.sharepreferences.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!DataLocalManager.getPreferenceBoolean("FIRST_INSTALL")){
            DataLocalManager.setPreferenceBoolean("FIRST_INSTALL",true);
            Toast.makeText(this, "First install app", Toast.LENGTH_SHORT).show();
        }

        User user = new User(1,"A","B");
        DataLocalManager.saveUser(user);

    }
}