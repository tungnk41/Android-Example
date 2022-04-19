package com.example.customview.extensions

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.widget.TextView
import androidx.core.widget.TextViewCompat

fun TextView.setGradientColor(arrColor: IntArray) {
    val paint = this.paint
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    val width = paint.measureText(this.text.toString())
    val shader = LinearGradient(0f,0f,width,0f,arrColor,null, Shader.TileMode.CLAMP)
    paint.shader = shader

}

fun TextView.setStrokeColor(arrColorStroke: IntArray, strokeWidth: Float, arrColor: IntArray) {

    val paint = this.paint
    paint.isAntiAlias = true
    val width = paint.measureText(this.text.toString())
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = strokeWidth
    val shaderStroke = LinearGradient(0f,0f,width,0f,arrColorStroke,null, Shader.TileMode.CLAMP)
    paint.shader = shaderStroke
}