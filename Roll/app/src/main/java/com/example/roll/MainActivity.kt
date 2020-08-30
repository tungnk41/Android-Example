package com.example.roll

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"
    private lateinit var txtContent:  TextView
    private lateinit var btnRoll: Button
    private lateinit var btnCountUp: Button
    private lateinit var btnReset: Button
    private lateinit var imgRoll: ImageView

    private  var rollValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtContent = findViewById(R.id.content)
        btnRoll = findViewById(R.id.btnRoll)
        btnCountUp = findViewById(R.id.btnCountUp)
        btnReset = findViewById(R.id.btnReset)
        imgRoll = findViewById(R.id.imgRoll)

        btnRoll.setOnClickListener {
            onBtnRollClicked()
        }

        btnCountUp.setOnClickListener {
            onBtnCountUpClicked()
        }

        btnReset.setOnClickListener {
            onBtnResetClicked()
        }
    }

    private fun onBtnRollClicked(){
        var randomNumber: Int
        do{
            randomNumber = (1..6).random()
        }while (rollValue == randomNumber)
        rollValue = randomNumber
        updateRollIView(rollValue)
    }

    private fun onBtnCountUpClicked(){
        val currentContent: String= txtContent.text.toString()

        if(currentContent == resources.getString(R.string.txtContent)){
            rollValue = 1
            updateRollIView(rollValue)
        }
        else if(currentContent == "6"){
            Toast.makeText(this, currentContent ,Toast.LENGTH_SHORT).show()
        }
        else{
            Log.i(TAG,rollValue.toString())
            rollValue += 1
            updateRollIView(rollValue)

        }
    }

    private fun onBtnResetClicked(){
        rollValue = 0
        updateRollIView(rollValue)
    }

    private fun updateRollIView(value: Int){
        //Update image view
        when(value){
            1->imgRoll.setImageResource(R.drawable.dice_1)
            2->imgRoll.setImageResource(R.drawable.dice_2)
            3->imgRoll.setImageResource(R.drawable.dice_3)
            4->imgRoll.setImageResource(R.drawable.dice_4)
            5->imgRoll.setImageResource(R.drawable.dice_5)
            6->imgRoll.setImageResource(R.drawable.dice_6)
            else -> {
                imgRoll.setImageResource(R.drawable.empty_dice)
            }
        }
        //Update text view
        txtContent.setText(value.toString())
        Toast.makeText(this, value.toString() ,Toast.LENGTH_SHORT).show()
    }


}