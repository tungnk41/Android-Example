package com.example.customview

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class ProgressBarView(context : Context, attrs : AttributeSet) : View(context,attrs) {
    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = Color.RED
        private const val DEFAULT_COLOR = Color.BLUE
        private const val DEFAULT_RADIUS = 10f
        private const val DEFAULT_STROKE = 5f
        private const val DEFAULT_VALUE = 50 //percent
    }

    private var colorBackground = DEFAULT_BACKGROUND_COLOR
    private var color = DEFAULT_COLOR
    private var radius = DEFAULT_RADIUS
    private var value = 50
    private val paint  = Paint()
    private val strokePaint  = Paint()

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs : AttributeSet){
        val typedArray = context.theme.obtainStyledAttributes(attrs,R.styleable.ProgressBarView,0,0)
        colorBackground = typedArray.getColor(R.styleable.ProgressBarView_pbColorBackground, DEFAULT_BACKGROUND_COLOR)
        color = typedArray.getColor(R.styleable.ProgressBarView_pbColor, DEFAULT_COLOR)
        radius = typedArray.getDimension(R.styleable.ProgressBarView_pbRadius, DEFAULT_RADIUS)
        value = typedArray.getInteger(R.styleable.ProgressBarView_pbValue, DEFAULT_VALUE)
        typedArray.recycle()
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        setMeasuredDimension(measuredWidth + 30, measuredHeight + 30)
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initDraw(canvas)
    }

    private fun initDraw(canvas: Canvas?) {
        paint.color = colorBackground
//        setGradientPaintColor()
        canvas?.drawRoundRect(RectF(0F,0F,measuredWidth.toFloat(),measuredHeight.toFloat()),radius,radius, paint)
//        paint.shader = null
        paint.color = color
        var widthPercent = 0F
        if(value <= 0) widthPercent = 0F
        else if(value >= 100) widthPercent = measuredWidth.toFloat()
        else widthPercent = value*(measuredWidth.toFloat()/100)

        val sizeBox = Path().apply {
            addRect(RectF(0F,0F,widthPercent,measuredHeight.toFloat()), Path.Direction.CW)
        }
        canvas?.save()
        canvas?.clipPath(sizeBox)
        canvas?.drawRoundRect(RectF(0F,0F,measuredWidth.toFloat(),measuredHeight.toFloat()),radius,radius, paint)
        canvas?.restore()

        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = Color.BLACK
        strokePaint.strokeWidth = 5F
        canvas?.drawRoundRect(RectF(4F,4F,measuredWidth.toFloat()-4,measuredHeight.toFloat()-4),radius,radius, strokePaint)
    }

    private fun setGradientPaintColor() {
        val colors = intArrayOf(
            ContextCompat.getColor(context, R.color.purple_500),
            ContextCompat.getColor(context, R.color.teal_700),
            ContextCompat.getColor(context, R.color.white),
        )
        paint.shader = LinearGradient(0f,0f,measuredWidth.toFloat(),0F,colors,null,Shader.TileMode.CLAMP)
//        paint.shader = LinearGradient(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),colors,null,Shader.TileMode.CLAMP)
    }

    fun setValue(v: Int) {
        value = v
        invalidate()
    }

}