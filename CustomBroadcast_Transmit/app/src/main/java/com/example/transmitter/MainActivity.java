package com.example.transmitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtBroadcast;
    EditText edtUserId;
    EditText edtUserName;
    Button btnSendBroadcast;

    private static final String BROADCAST_CUSTOM_STRING_ACTION = "broadcast.custom.string.action";
    private static final String BROADCAST_CUSTOM_OBJECT_ACTION = "broadcast.custom.object.action";
    private static final String BROADCAST_CUSTOM_LIST_OBJECT_ACTION = "broadcast.custom.list.object.action";
    private static final String DATA_STRING = "data.string";
    private static final String DATA_OBJECT = "data.object";
    private static final String DATA_LIST_OBJECT = "data.list.object";
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_CUSTOM_STRING_ACTION)){
                String strReceive = intent.getStringExtra(DATA_STRING);
                Toast.makeText(context, strReceive, Toast.LENGTH_SHORT).show();
            }
            if (intent.getAction().equals(BROADCAST_CUSTOM_OBJECT_ACTION)) {
                String jsonObject = intent.getStringExtra(DATA_OBJECT);
                Gson gson = new Gson();
                User user = gson.fromJson(jsonObject,User.class);
                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show();
            }
            if (intent.getAction().equals(BROADCAST_CUSTOM_LIST_OBJECT_ACTION)) {
                String strJsonArray = intent.getStringExtra(DATA_LIST_OBJECT);
                Gson gson = new Gson();
                List<User> listUser = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(strJsonArray);
                    JSONObject jsonObject;
                    User user;

                    for (int i = 0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        user = gson.fromJson(jsonObject.toString(),User.class);
                        listUser.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, listUser.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendBroadcast = findViewById(R.id.btnSendBroadcast);
        edtBroadcast = findViewById(R.id.edtBroadcast);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserId = findViewById(R.id.edtUserId);

        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStringBroadcast();
                sendObjectBroadcast();
                sendListObjectBroadcast();
            }
        });
    }

    private void sendStringBroadcast(){
        Intent intent = new Intent(BROADCAST_CUSTOM_STRING_ACTION);
        String strData = edtBroadcast.getText().toString().trim();
        if(TextUtils.isEmpty(strData)){
            Toast.makeText(this, "Text empty", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(DATA_STRING,strData);
        sendBroadcast(intent);
    }

    private void  sendObjectBroadcast(){
        String strUserId = edtUserId.getText().toString();
        String strUserName = edtUserName.getText().toString().trim();
        if(TextUtils.isEmpty(strUserId) || TextUtils.isEmpty(strUserName)){
            Toast.makeText(this, "Id or Username empty", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(strUserId,strUserName);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(user);
        Intent intent = new Intent(BROADCAST_CUSTOM_OBJECT_ACTION);
        intent.putExtra(DATA_OBJECT,jsonObject);
        sendBroadcast(intent);
    }

    private void sendListObjectBroadcast(){
        String strUserId = edtUserId.getText().toString();
        String strUserName = edtUserName.getText().toString().trim();
        if(TextUtils.isEmpty(strUserId) || TextUtils.isEmpty(strUserName)){
            Toast.makeText(this, "Id or Username empty", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(strUserId,strUserName);
        List<User> listUser = new ArrayList<>();
        listUser.add(user);
        Gson gson = new Gson();
        JsonArray jsonArray = (JsonArray) gson.toJsonTree(listUser);
        String strJsonArray = jsonArray.toString();
        Intent intent = new Intent(BROADCAST_CUSTOM_LIST_OBJECT_ACTION);
        intent.putExtra(DATA_LIST_OBJECT,strJsonArray);
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_CUSTOM_STRING_ACTION);
        intentFilter.addAction(BROADCAST_CUSTOM_OBJECT_ACTION);
        intentFilter.addAction(BROADCAST_CUSTOM_LIST_OBJECT_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}