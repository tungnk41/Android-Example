package com.example.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*
* View : Activity xml : Hien thi giao dien
* Controller : Activity : Nghe action from view, khoi tao model, send cac action tuong ung toi model, tra lai ket qua toi view, xu ly logic business
* Model : User : Hien thi data, include rule for data
* */
public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });
    }

    private void clickLogin() {
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        User user = new User(strEmail,strPassword);
        if(user.isValidEmail() && user.isValidPassword()){
            Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Email or Password invalid", Toast.LENGTH_SHORT).show();
        }


    }


}