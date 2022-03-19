package com.example.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MSG_SAY_HELLO = 1
        private const val MSG_REGISTER_CLIENT = 2
        private const val MSG_UNREGISTER_CLIENT = 3
        private const val MSG_SEND_DATA_TO_CLIENT = 4
        private const val MSG_SEND_DATA_TO_SERVICE = 5
        const val TAG = "MainActivity"
    }

    private var mService: Messenger? = null
    private var mMessenger: Messenger? = null
    private var bound : Boolean = false
    private var isRegistered : Boolean = false

    private lateinit var btnSendAction: Button
    private lateinit var btnRegister: Button

    private val mServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            bound = true
        }
        override fun onServiceDisconnected(className: ComponentName?) {
            mService = null
            bound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMessenger = Messenger(IncomingHandler(this))

        btnSendAction = findViewById(R.id.btnSendAction)
        btnSendAction.setOnClickListener {
            sendAction()
        }
        btnRegister = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            if(!isRegistered) {
                register()
                isRegistered = true
                btnRegister.setText("UnRegistered")
            }
            else {
                unregister()
                isRegistered = false
                btnRegister.setText("Registered")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this, MessengerService::class.java),mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if(bound) {
            unbindService(mServiceConnection)
            bound = false
        }
    }

    inner class IncomingHandler(context: Context) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                MSG_SEND_DATA_TO_CLIENT -> { handleAction(msg) }
                else -> super.handleMessage(msg)
            }
        }
    }

    private fun register() {
        if(!bound) return
        val msg : Message = Message.obtain(null,MSG_REGISTER_CLIENT)
        msg.replyTo = mMessenger
        try {
            mService?.send(msg)
        }
        catch (e :Exception){
            e.printStackTrace()
        }
    }

    private fun unregister() {
        if(!bound) return
        val msg : Message = Message.obtain(null,MSG_UNREGISTER_CLIENT)
        msg.replyTo = mMessenger
        try {
            mService?.send(msg)
        }
        catch (e :Exception){
            e.printStackTrace()
        }
    }

    private fun sendAction() {
        if(!bound) return

        val msg : Message = Message.obtain(null, MSG_SEND_DATA_TO_SERVICE)
        try {
            mService?.send(msg)
        }
        catch (e :Exception){
            e.printStackTrace()
        }
    }

    private fun handleAction(msg: Message){
        try {
            Log.d(TAG, "handleAction: " + msg.obj.toString())
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }
}