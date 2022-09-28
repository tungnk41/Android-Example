package com.example.okhttp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.okhttp.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var tvContent : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContent = findViewById(R.id.tvContent)

        try {
            run()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun run() {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://63331e4f573c03ab0b58804a.mockapi.io/api/v1/test")
            .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(request: Request?, e: IOException?) {
                e?.printStackTrace()
            }

            override fun onResponse(response: Response?) {
                if (response != null && response.isSuccessful) {
                    val listResponseAPI = Gson().fromJson<List<ResponseAPI>>(response.body().string(),  object : TypeToken<List<ResponseAPI>>() {}.type)
                    runOnUiThread {
                        tvContent.text = listResponseAPI.toString()
                    }
                }
            }
        })
    }
}

data class ResponseAPI(val id: Int,val title: String,val description: String, val thumbnail: String, var images : List<String>)