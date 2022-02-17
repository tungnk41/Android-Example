package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class IntentServiceExt : JobIntentService() {

    companion object{
        val JOB_ID = 1000
        fun enqueueWork(context: Context?, work: Intent?) {
            if(context != null && work != null){
                enqueueWork(context, IntentServiceExt::class.java, JOB_ID, work)
            }
        }
    }

    override fun onHandleWork(intent: Intent) {
        Log.d("TAG", "onHandleWork: ")

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this,AlarmReceiver::class.java)
        intent.action = "ALARM_ACTION"
        intent.putExtra("KEY_ALARM","Message")
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        val ALARM_DELAY_SECOND = 5
        val timeUTC = System.currentTimeMillis() + ALARM_DELAY_SECOND * 1000L
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeUTC,pendingIntent)
    }

}