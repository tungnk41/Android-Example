package com.example.sharepreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sharepreferences.model.User;

public class MainActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

    Button btnSetPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!DataLocalManager.getInstance().getPreferenceBoolean("FIRST_INSTALL")){
            DataLocalManager.getInstance().setPreferenceBoolean("FIRST_INSTALL",true);
            Toast.makeText(this, "First install app", Toast.LENGTH_SHORT).show();
        }
        btnSetPref = findViewById(R.id.btnSetPref);

        btnSetPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPrefClicked();
            }
        });

        DataLocalManager.getInstance().setPreferenceString("STRING","a");

    }

    private void onSetPrefClicked() {
        User user = new User(1,"A","B");
        DataLocalManager.getInstance().saveUser(user);

        DataLocalManager.getInstance().setPreferenceString("STRING","b");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(DataLocalManager.KEY_STRING)){
            Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataLocalManager.getInstance().registerPref(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataLocalManager.getInstance().unRegisterPref(this);
    }
}