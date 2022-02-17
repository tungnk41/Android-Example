package com.example.sqldelight.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sqldelight.R
import com.example.sqldelight.view.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin :Button
    lateinit var tvUsername : EditText
    lateinit var tvPassword :EditText
    val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        tvUsername = findViewById(R.id.tvUsername)
        tvPassword = findViewById(R.id.tvPassword)

        btnLogin.setOnClickListener {
            viewModel.login(tvUsername.text.toString(),tvPassword.text.toString(), onSuccess = { user->
                if(user != null){
                    startActivity(Intent(this,MainActivity::class.java).putExtra("id",user.id))
                    finish()
                }
            })
        }
    }

}