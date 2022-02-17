package com.example.tranferviewdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tranferviewdata.model.DataSerial;
import com.example.tranferviewdata.model.User;

public class ReceiverActivity extends AppCompatActivity {
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        tvContent = findViewById(R.id.tvContent);
        //getSerial();
        getIntentExtra();
        //getBundle();
        //getParacel();


    }

    void getParacel(){
        User user = getIntent().getParcelableExtra("userData");
        tvContent.setText(user.toString());
    }

    void getIntentExtra(){
        String data = getIntent().getExtras().getString("KEY_INTENT");
        tvContent.setText(data);
    }

    void getBundle(){
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("KEY_BUNDLE");
        tvContent.setText(data);
    }


    void getSerial(){
        DataSerial dataSerial = (DataSerial) getIntent().getSerializableExtra("dataSerial");
        tvContent.setText(dataSerial.toString());

    }


}