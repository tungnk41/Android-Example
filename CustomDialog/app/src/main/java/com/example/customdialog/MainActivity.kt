package com.example.customdialog

import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var btnShowDialog: Button
    private lateinit var alertDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowDialog = findViewById(R.id.btnShowDialog)
        btnShowDialog.setOnClickListener {
//            showDialog()
            showAlertDialog()
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
        var btnSubmit = dialog.findViewById<Button>(R.id.btnSubmit)

        val btnClose = dialog.findViewById<ImageView>(R.id.btnClose)
        val btnNext = dialog.findViewById<Button>(R.id.btnNext)

        btnSubmit.setOnClickListener {
            Log.d("TAG", "showDialog: OnClicked 1")
            dialog.dismiss()
        }
        btnNext.setOnClickListener {
            dialog.setContentView(R.layout.custom_next_dialog)
            val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmitNext)
            btnSubmit.setOnClickListener {
                Log.d("TAG", "showDialog: OnClicked 2")
                dialog.dismiss()
            }
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showAlertDialog() {
        alertDialog = Dialog(this)
        alertDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        alertDialog.setContentView(R.layout.alert_dialog_view)
        val displayMetrics = resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels
        val screenHeightPx = displayMetrics.heightPixels
        alertDialog.window?.setLayout( (screenWidthPx * 0.8).toInt(), ConstraintLayout.LayoutParams.WRAP_CONTENT)
//        val layoutInflater = layoutInflater
//        val view = layoutInflater.inflate(R.layout.alert_dialog_view, null)
//        alertDialog.setView(view)
//        alertDialog.setMessage("ABCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCccc")
        alertDialog.show()
    }
}