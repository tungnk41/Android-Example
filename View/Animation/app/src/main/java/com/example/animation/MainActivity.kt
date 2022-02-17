package com.example.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart : Button
    private lateinit var btnReset : Button
    private lateinit var imgImage : ImageView
    private var originX : Float = 0.0f
    private var originY : Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)
        imgImage = findViewById(R.id.imgImage)

        originX = imgImage.x
        originY = imgImage.y
        btnStart.setOnClickListener {
            handleStartAnimation()
        }

        btnReset.setOnClickListener {
            handleResetAnimation()
        }
    }

    fun handleStartAnimation(){

        val animatorX = ObjectAnimator.ofFloat(imgImage,View.X,imgImage.x + 200)
        animatorX.setDuration(1000)
        val animatorY = ObjectAnimator.ofFloat(imgImage,View.Y,imgImage.y + 200)
        animatorY.setDuration(1000)
        val animatorAlpha = ObjectAnimator.ofFloat(imgImage, View.ALPHA,1.0f,0.0f)
        animatorAlpha.setDuration(1000)
        val animatorRotate = ObjectAnimator.ofFloat(imgImage, View.ROTATION,0f,360f)
        animatorRotate.setDuration(1000)


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorX,animatorY,animatorAlpha,animatorRotate)
        animatorSet.start()
    }

    fun handleResetAnimation(){
        imgImage.x = originX
        imgImage.y = originY
        imgImage.alpha = 1.0f
    }
}