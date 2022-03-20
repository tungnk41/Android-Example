package com.example.host

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.iface.IFaceAidl

class HostService : Service() {

    companion object {
        const val TAG = "HostService"
    }

    private var mData = 0

    private val mBinder = object : IFaceAidl.Stub() {
        override fun sendData(data: Int) {
            mData = data + 1
            Log.d(TAG, "sendData: Host " + data)
        }

        override fun receiveData(): Int {
            return mData
        }

    }
    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }
}