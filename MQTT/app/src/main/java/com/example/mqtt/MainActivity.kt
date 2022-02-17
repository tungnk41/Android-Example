package com.example.mqtt

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MainActivity : AppCompatActivity() {
    private lateinit var client : MqttAndroidClient
    private lateinit var btnPublish : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPublish = findViewById(R.id.btnPublish)
        initMQTT()

        btnPublish.setOnClickListener {
            publish("Pub/Data", "ON")
        }
    }


    fun initMQTT(){
        val host = "tcp://broker.hivemq.com:1883"
        val clientId = MqttClient.generateClientId()

        client = MqttAndroidClient(applicationContext,host,clientId)
        connect(client)
    }

    fun connect(client : MqttAndroidClient){
        try {
            val username = "tuhn41"
            val password = "TuHn41TnK00"

            val connectOption = MqttConnectOptions()
            connectOption.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1
//            connectOption.userName = username
//            connectOption.password = password.toCharArray()

            client.connect(connectOption,object : IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connect onSuccess: ")
                    subcribe("Pub/Data")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Connect onFailure: ")
                }
            })
        }catch (e : MqttException){
            e.printStackTrace()
        }

    }

    fun publish(topic : String, msg : String){
        try {
            if(!client.isConnected){
                connect(client)
            }

            val message = MqttMessage()
            message.payload = msg.encodeToByteArray()
            message.qos = 0
            client.publish(topic,message, null, object : IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Publish onSuccess: ")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Publish onFailure: ")
                }
            })
        }catch (e : MqttException){
            e.printStackTrace()
        }
    }

    fun subcribe(topic : String){
        try {
            client.subscribe(topic,0, object  : IMqttMessageListener{
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let{ Log.d(TAG, "messageArrived: " + topic + " , " + String(message.payload)) }
                }
            })
        }catch (e : MqttException){
            e.printStackTrace()
        }
    }

    fun unSubcribe(client : MqttAndroidClient, topic: String){
        try {
            client.unsubscribe(topic)
        }catch (e : MqttException){
            e.printStackTrace()
        }

    }

}