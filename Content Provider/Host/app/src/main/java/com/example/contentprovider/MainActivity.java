package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentprovider.database.UserDatabase;
import com.example.contentprovider.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserName;
    private TextView tvAddress;
    private Button btnAddData;
    private Button btnGetData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvUserAddress);
        btnAddData = findViewById(R.id.btnAddData);
        btnGetData = findViewById(R.id.btnGetData);


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initDataDB();
                //initDataByProvider();
            }
        });


        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDataDB();
                getDataByProvider();
            }
        });
    }

    private void initDataDB(){
        //UserDatabase.getInstance().userDAO.deleteAll();

        //Add data with custome UserDatabase
        User user = new User("abc", "add");
        UserDatabase.getInstance().userDAO.insertUser(user);
    }

    private void initDataByProvider(){
        //Add data with content provider
        ContentValues values = new ContentValues();
        values.put(ContentPrividerExt.NAME,"name abc");
        values.put(ContentPrividerExt.ADDRESS,"address abc");
        Uri uri = getContentResolver().insert(ContentPrividerExt.CONTENT_URI,values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
    }

    private void getDataDB(){
        List<User> listUser = UserDatabase.getInstance().userDAO.getListUser();
        if(listUser.size() != 0){
            for (int i = 0; i < listUser.size(); i++) {
               User user = listUser.get(i);
                tvUserName.setText(user.getName());
                tvAddress.setText(user.getAddress());

            }
        }
    }

    private void getDataByProvider(){
        final String AUTHORITY = "com.example.contentprovider.provider";
        final String TABLE_NAME = UserDatabase.TABLE_NAME;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserDatabase.getInstance().close();
    }
}