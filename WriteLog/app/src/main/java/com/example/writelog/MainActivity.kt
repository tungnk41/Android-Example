package com.example.writelog

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        writeLogToFile(this,"ABC","D")
        test()


    }

    private fun test() {
        Log.d("ABK", "TTTTTTTTTTTTTTT")
        Log.v("ABC", "TTTTTTTTTTTTTTT")
        Log.e("ABC", "TTTTTTTTTTTTTTT")
        Log.i("ABC", "TTTTTTTTTTTTTTT")
    }

    private fun writeLogToFile(context: Context, tag: String, priority: String = "D") {
        val fileName = "logcat.txt"
        val file = File(context.externalCacheDir, fileName)
        if (!file.exists()) file.createNewFile()

         var cmdEnd = " *:F"
        val newEntry = " $tag:$priority"
        if (!cmdEnd.contains(newEntry)) {
            cmdEnd = newEntry + cmdEnd
        }
        val command = "logcat -f " + file.absolutePath + cmdEnd
        Runtime.getRuntime().exec(command)
    }

//    fun writeLogToFile(context: Context, tag: String, type: String) {
//        val fileName = "logcat.txt"
//        val file = File(context.externalCacheDir, fileName)
//        if (!file.exists()) file.createNewFile()
//        val command = "logcat -f " + file.getAbsolutePath()
//        Runtime.getRuntime().exec(command)
//    }
}