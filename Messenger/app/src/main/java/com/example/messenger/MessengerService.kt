package com.example.messenger

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import com.example.messenger.model.ModelData

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

//    private fun sendDataToClients() {
//        mClients.forEach { client ->
//            client.send(Message.obtain(null,MSG_SEND_DATA_TO_CLIENT, ModelData(id = 1,name = "data 1")))
//        }
//    }

    //For Different Process
    private fun sendDataToClients() {
        mClients.forEach { client ->
            val bundle = Bundle().apply {
                putParcelableArrayList("data", arrayListOf<ModelData>(ModelData(id = 1, name = "text 1"),ModelData(id = 1, name = "text 1")))
            }

            val msg : Message = Message.obtain(null,MSG_SEND_DATA_TO_CLIENT)
            msg.data = bundle
            client.send(msg)
        }
    }

}