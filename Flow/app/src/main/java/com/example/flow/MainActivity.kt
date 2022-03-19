package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
import com.example.flow.model.DataModelUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private var count = 0

    lateinit var tvTextView: TextView
    lateinit var btnFlow: Button
    lateinit var btnStateFlow: Button
    lateinit var btnShareFlow: Button
    lateinit var btnConnectShareFlow: Button
    lateinit var btnReset: Button
    private val data = DataModelUI(1,1)
    private val data2 = data
    private val data3 = DataModelUI(3,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        tvTextView = findViewById(R.id.tvTextView)
        btnFlow = findViewById(R.id.btnFlow)
        btnStateFlow = findViewById(R.id.btnStateFlow)
        btnShareFlow = findViewById(R.id.btnShareFlow)
        btnConnectShareFlow = findViewById(R.id.btnConnectShareFlow)
        btnReset = findViewById(R.id.btnReset)

        lifecycleScope.launch {
            viewModel._flow.collect {
                Log.d(TAG, "Flow : " + it)
            }

            viewModel._shareflow.collect {
                Log.d(TAG, "Connect Flow : " + it)
            }

            viewModel._shareflow.collect {
                delay(1000)
                Log.d(TAG, "Share Flow : " + it)
            }
        }

        btnFlow.setOnClickListener {
            val _count = ++count
            lifecycleScope.launch {
                viewModel._flow.collect {
                    Log.d(TAG, "Flow $_count : " + it)
                }
            }

        }

        btnStateFlow.setOnClickListener {
            val _count = ++count
            lifecycleScope.launch {
                viewModel._stateflow.collect {
                    Log.d(TAG, "State Flow $_count: " + it)
                }
            }
        }

        btnShareFlow.setOnClickListener {
            val _count = ++count
            lifecycleScope.launch {
                viewModel._shareflow.collect {
                    delay(1000)
                    Log.d(TAG, "Share Flow $_count: " + it)
                }
            }
        }


        btnConnectShareFlow.setOnClickListener {
            val _count = ++count
            lifecycleScope.launch {
                viewModel._mutableShareflow.collect {
                    Log.d(TAG, "Connect Flow $_count: " + it.toString() + "  " + System.identityHashCode(it.hashCode()))
                }
            }
        }

        btnReset.setOnClickListener {
            count = 0
            viewModel._mutableShareflow.tryEmit(data)
            viewModel._mutableShareflow.tryEmit(da)
            viewModel._mutableShareflow.tryEmit(data3)

        }

    }

}