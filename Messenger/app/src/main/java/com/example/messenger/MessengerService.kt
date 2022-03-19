package com.example.messenger

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log

class MessengerService : Service() {

    companion object {
        private const val MSG_SAY_HELLO = 1
        private const val MSG_REGISTER_CLIENT = 2
        private const val MSG_UNREGISTER_CLIENT = 3
        private const val MSG_SEND_DATA_TO_CLIENT = 4
        private const val MSG_SEND_DATA_TO_SERVICE = 5
        const val TAG = "MessengerService"
    }
    private lateinit var mMessenger: Messenger
    private var mClients = mutableListOf<Messenger>()

    inner class IncomingHandler(context: Context) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "handleMessage: " + msg.what)
            when(msg.what) {
                MSG_SAY_HELLO -> { Log.d(TAG, "handleMessage: " + msg.obj.toString())}
                MSG_REGISTER_CLIENT -> { mClients.add(msg.replyTo) }
                MSG_UNREGISTER_CLIENT -> { mClients.remove(msg.replyTo) }
                MSG_SEND_DATA_TO_SERVICE -> { sendDataToClients() }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }

    private fun sendDataToClients() {
        mClients.forEach { client ->
            client.send(Message.obtain(null,MSG_SEND_DATA_TO_CLIENT,"Data send from Service to Client"))
        }
    }

}