package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartAlarm : Button
    private lateinit var btnStopAlarm : Button

    private lateinit var alarmManager : AlarmManager
    private lateinit var pendingIntent : PendingIntent

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartAlarm = findViewById(R.id.btnStartAlarm)
        btnStopAlarm = findViewById(R.id.btnStopAlarm)

        configAlarm()

        btnStartAlarm.setOnClickListener(View.OnClickListener(){
            startAlarm()
        })

        btnStopAlarm.setOnClickListener(View.OnClickListener(){
            stopAlarm()
        })
    }

    fun configAlarm() {
        alarmReceiver = AlarmReceiver()
        this.registerReceiver(alarmReceiver, IntentFilter("android.intent.action.BOOT_COMPLETED"))


        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this,AlarmReceiver::class.java)
        intent.action = "ALARM_ACTION"
        intent.putExtra("KEY_ALARM","Message")
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
    }

    fun startAlarm(){
        /*
        Follow :
        When click start button : AlarmManager send Pending Intent to Alarm Receiver -> Filter Alarm-Action -> Execute code
        When Boot Completed -> Alarm Receiver receive action BOOT_COMPLETED -> Send IntentService -> IntentService send Pending Intent to Alarm Receiver with Alarm-Action
         */
        val ALARM_DELAY_SECOND = 2
        val timeUTC = System.currentTimeMillis() + ALARM_DELAY_SECOND * 1000L

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,12)
        calendar.set(Calendar.MINUTE,23)
        calendar.set(Calendar.SECOND,0)


//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeUTC,pendingIntent)
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
    }

    fun stopAlarm() {
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this,"Alarm Cancelled",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(alarmReceiver);
    }
}
