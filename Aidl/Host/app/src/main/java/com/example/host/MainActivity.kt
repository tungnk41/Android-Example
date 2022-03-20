package com.example.host

import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.iface.IFaceAidl

class MainActivity : AppCompatActivity() {
    private lateinit var btnSendData: Button
    private lateinit var btnReceiveData: Button
    private lateinit var mService: IFaceAidl
    private var bound: Boolean = false
    private val mServiceConnection = object: ServiceConnection{
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            mService = IFaceAidl.Stub.asInterface(binder)
            bound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            bound = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSendData = findViewById(R.id.btnSendData)
        btnReceiveData = findViewById(R.id.btnReceiveData)

        btnSendData.setOnClickListener {
            val data = 1
            mService.sendData(data)
        }

        btnReceiveData.setOnClickListener {
            val data = mService.receiveData()
            Log.d(TAG, "btnReceiveData: Host " + data)

        }
    }

    override fun onStart() {
        super.onStart()
        bindService()
    }

    override fun onStop() {
        super.onStop()
        unbindService(mServiceConnection)
    }

    private fun  bindService() {
        bindService(Intent(this, HostService::class.java),mServiceConnection, Context.BIND_AUTO_CREATE)
    }
}