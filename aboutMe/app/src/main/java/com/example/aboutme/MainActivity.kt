package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {
    private lateinit var txtEditName: EditText
    private lateinit var txtName: TextView
    private lateinit var btnDone : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEditName = findViewById(R.id.txtEditName)
        txtName = findViewById(R.id.txtName)
        btnDone = findViewById(R.id.btnDone)

        btnDone.setOnClickListener {
            txtName.text = txtEditName.text
            txtName.visibility = View.VISIBLE
            txtEditName.visibility = View.GONE

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken,0)
        }

        txtName.setOnClickListener{
            txtName.visibility = View.GONE
            txtEditName.visibility = View.VISIBLE

            txtEditName.requestFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(txtEditName,0)
        }

    }


}