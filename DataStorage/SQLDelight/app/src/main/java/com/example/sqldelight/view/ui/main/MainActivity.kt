package com.example.sqldelight.view.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.sqldelight.R
import com.example.sqldelight.view.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnLogout: Button
    lateinit var edtTitle: EditText
    lateinit var edtContent: EditText
    lateinit var tvResult: TextView
    lateinit var btnAddNote: Button
    lateinit var btnRemoveAll: Button
    val viewModel = MainViewModel()
    var user_id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user_id = intent.getLongExtra("id",-1)

        btnLogout = findViewById(R.id.btnLogout)
        edtTitle = findViewById(R.id.edtTitle)
        tvResult = findViewById(R.id.tvResult)
        btnAddNote = findViewById(R.id.btnAddNote)
        btnRemoveAll= findViewById(R.id.btnRemoveAll)
        edtContent = findViewById(R.id.edtContent)

        viewModel.findAllNote(user_id)
        viewModel.result.observe(this,{
            tvResult.text = it?.toString()
        })

        btnLogout.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        btnAddNote.setOnClickListener {
            viewModel.insertNote(edtTitle.text.toString(),edtContent.text.toString(),user_id)
        }
        btnRemoveAll.setOnClickListener {
            viewModel.deleteAll(user_id)
        }
    }


}