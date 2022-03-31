package com.example.customdialog

import android.app.ActionBar
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var btnShowDialog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowDialog = findViewById(R.id.btnShowDialog)
        btnShowDialog.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)

        val edtFirst = dialog.findViewById<EditText>(R.id.edtFirst)
        val edtSecond = dialog.findViewById<EditText>(R.id.edtSecond)
        val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmmit)
        val btnClose = dialog.findViewById<ImageView>(R.id.btnClose)

        btnSubmit.setOnClickListener {
            Log.d("TAG", "showDialog: OnClicked " + edtFirst.toString() + " ," + edtSecond.toString())
            dialog.dismiss()
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}