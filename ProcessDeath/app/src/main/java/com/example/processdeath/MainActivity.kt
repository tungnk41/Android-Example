package com.example.processdeath

import android.content.ContentValues.TAG
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var tvData: TextView
    private lateinit var btnSet: Button
    private var dataTest : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvData = findViewById(R.id.tvData)
        btnSet = findViewById(R.id.btnSet)

//        viewModel.dataViewModel.observe(this) {
//            tvData.setText(it)
//        }
        btnSet.setOnClickListener {
            dataTest += 1
            tvData.text = dataTest.toString()
        }

        Log.d(TAG, "onCreate: " + savedInstanceState?.getInt("data"))

        savedInstanceState?.let {
            tvData.text = (savedInstanceState?.getInt("data") ?: 0).toString()
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged: ")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState?.getInt("data"))
        super.onRestoreInstanceState(savedInstanceState)

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
        outState.putInt("data",dataTest)
    }
}