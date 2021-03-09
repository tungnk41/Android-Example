package com.example.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginInterface{

    private LoginPresenter mLoginPresenter;
    private EditText email,password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginPresenter = new LoginPresenter(this);
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
        User user = new User(strEmail, strPassword);
        mLoginPresenter.login(user);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError() {
        Toast.makeText(this, "Email or Password invalid", Toast.LENGTH_SHORT).show();
    }
}