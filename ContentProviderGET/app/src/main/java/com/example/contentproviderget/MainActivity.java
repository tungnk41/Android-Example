package com.example.contentproviderget;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvUserName;
    TextView tvAddress;
    Button btnGetData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        btnGetData = findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickGetData();
            }
        });
    }

    private void clickGetData(){
        final String AUTHORITY = "com.example.contentprovider.provider";
        final String TABLE_NAME = "user";
        final String URI = "content://" + AUTHORITY + "/" + TABLE_NAME;
        final Uri CONTENT_URI = Uri.parse(URI);

        //Get all data from database
        Cursor cursor = getContentResolver().query(CONTENT_URI,null,null,null,null);
        cursor.moveToFirst();
        do{
            int id = (int)cursor.getLong(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            tvUserName.setText(name);
            tvAddress.setText(address);
        }while (cursor.moveToNext());
        cursor.close();
    }
}