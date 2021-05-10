package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals("ALARM_ACTION")){
            Log.d("TAG", "ALARM_ACTION")
            val message = intent?.getStringExtra("KEY_ALARM")
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

        if(intent?.action.equals("android.intent.action.BOOT_COMPLETED")){
            Log.d("TAG", "android.intent.action.BOOT_COMPLETED ")
            val intent = Intent(context,IntentServiceExt::class.java)
            IntentServiceExt.enqueueWork(context,intent)
        }
    }
}