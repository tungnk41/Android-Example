package com.example.tranferviewdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tranferviewdata.model.DataSerial;
import com.example.tranferviewdata.model.User;

public class MainActivity extends AppCompatActivity {

    private Button btnTranferAct;
    private TextView tvRevFrag;
    private Button btnTranferFrag;

    RevFragment revFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTranferAct = findViewById(R.id.btnTranferAct);
        btnTranferFrag = findViewById(R.id.btnTranferFrag);
        tvRevFrag = findViewById(R.id.tvRevFrag);
        btnTranferAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendDataParacel();
                sendDataIntentExtra();
                //sendDataBundle();
                //sendDataSerial();
            }
        });

        btnTranferFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToFrag();
            }
        });

        initFrag();
    }


    void initFrag(){
        String data = "Data from Activity to Fragment Init";
        Bundle bundle = new Bundle();
        bundle.putString("KEY_FRAG",data);

        revFragment = new RevFragment();
        revFragment.setArguments(bundle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContent,revFragment);
        fragmentTransaction.commit();
    }

    //Tranfer data to fragment by call function
    private void sendDataToFrag() {
        String data = "Send data from Activity to Fragment";
        Bundle bundle = new Bundle();
        bundle.putString("KEY_FRAG",data);
        revFragment.setArguments(bundle);

        revFragment.revDataFromAct(bundle);
    }

    //Using Paracelable
    void sendDataParacel(){
        User user = new User(1,"userName","userAddress");

        Intent intent = new Intent(MainActivity.this,ReceiverActivity.class);
        intent.putExtra("userData", user);
        startActivity(intent);
    }

    //Using Intent
    void sendDataIntentExtra(){
        String data = "Data intent extra";
        Intent intent = new Intent(MainActivity.this,ReceiverActivity.class);
        intent.putExtra("KEY_INTENT",data);
        startActivity(intent);
    }

    //Using Bundle
    void sendDataBundle(){
        Intent intent = new Intent(MainActivity.this,ReceiverActivity.class);
        String data = "Data bundle";

        Bundle bundle = new Bundle();
        bundle.putString("KEY_BUNDLE",data);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    //Using Serializable
    void sendDataSerial(){
        Intent intent = new Intent(MainActivity.this,ReceiverActivity.class);
        DataSerial dataSerial =  new DataSerial(1,"Data Serial");
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSerial",dataSerial);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    public void callFromFrag(String data){
        tvRevFrag.setText(data);
    }
}