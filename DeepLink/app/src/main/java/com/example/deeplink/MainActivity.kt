package com.example.deeplink

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

//adb shell am start -a android.intent.action.VIEW -d "deeplink://example.com" com.example.deeplink
//adb shell am start -W -a android.intent.action.VIEW -d "https://example.applink.com"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {

        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        Log.d("TAG", "handleIntent 1 : ${intent.action} + ${intent.data}")
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { id ->
                Log.d("TAG", "handleIntent: $id")
            }
        }
    }
}