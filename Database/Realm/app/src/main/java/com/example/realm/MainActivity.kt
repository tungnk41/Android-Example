package com.example.realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    val m_realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun onDestroy() {
        super.onDestroy()
        if(m_realm != null){
            m_realm.close()
        }
    }
}