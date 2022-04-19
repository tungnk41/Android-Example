package com.example.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.customview.extensions.setGradientColor

class GradientTextView(context : Context, attrs : AttributeSet): AppCompatTextView(context,attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawGradientColorStroke(canvas,intArrayOf(Color.GREEN,Color.BLUE))
        super.onDraw(canvas)
    }

    fun drawGradientColorBackground(arrColor: IntArray) {
        val paint = this.paint
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        val width = paint.measureText(this.text.toString())
        val shader = LinearGradient(0f,0f,width,0f,arrColor,null, Shader.TileMode.CLAMP)
        paint.shader = shader
    }

    private fun drawGradientColorStroke(canvas: Canvas?,arrColor: IntArray) {
        val paint = this.paint
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        val width = paint.measureText(this.text.toString())
        val shader = LinearGradient(0f,0f,width,0f, arrColor,null, Shader.TileMode.CLAMP)
        paint.shader = shader
    }

}