package com.example.client

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

    companion object {
        const val HOST_INTENT_ACTION = "com.example.service.BIND"
        const val HOST_PACKAGE = "com.example.host"
    }
    private lateinit var btnSendData: Button
    private lateinit var btnReceiveData: Button
    private lateinit var mService: IFaceAidl
    private var bound : Boolean = false
    private val mServiceConnetion = object : ServiceConnection{
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
            mService.sendData(1000)
        }

        btnReceiveData.setOnClickListener {
            val data = mService.receiveData()
            Log.d(TAG, "btnReceiveData Client: " + data)
        }
    }

    override fun onStart() {
        super.onStart()
        bindService()
    }

    private fun bindService() {
        val intent = Intent()
        intent.action = HOST_INTENT_ACTION
        intent.`package` = HOST_PACKAGE
        bindService(intent,mServiceConnetion, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(mServiceConnetion)
    }
}