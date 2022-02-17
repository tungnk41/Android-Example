package com.example.lifecycle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lifecycle.MainActivity;
import com.example.lifecycle.R;
import com.example.lifecycle.fragment.FragmentHost;
import com.example.lifecycle.fragment.Fragment_1;
import com.example.lifecycle.utils.Utils;

public class ActivityHost extends AppCompatActivity {
    private Button btnStartActivity1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        btnStartActivity1 = findViewById(R.id.btnStartActivity1);

        btnStartActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHost.this, Activity_1.class));
            }
        });
    }
}