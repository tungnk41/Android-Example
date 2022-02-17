package com.example.firebasefcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.firebasefcm.InAppMessage.IAMClickListener
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var btnToken : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToken = findViewById(R.id.btnToken)

        btnToken.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    Log.d("TAG", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("TAG", "onCreate: token " + token)
            }

//            FirebaseInAppMessaging.getInstance().triggerEvent("Draft campaign")
        }

        FirebaseInAppMessaging.getInstance().addClickListener(IAMClickListener())
    }
}