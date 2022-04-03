package com.example.customview

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View.MeasureSpec
import android.view.View as View

class EmotionView(context : Context, attrs : AttributeSet) : View(context,attrs) {
    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.RED
        private const val DEFAULT_RADIUS = 50f
        private const val DEFAULT_BORDER_WIDTH = 4.0f
    }

    private val isInitConfig = true
    private val paint  = Paint()
    private val mousePath = Path()

    private var colorBackground = DEFAULT_BACKGROUND_COLOR
    private var colorEyes = DEFAULT_EYES_COLOR
    private var radius = DEFAULT_RADIUS
    private var size = 0
    private var prePoint = Pair(0f,0f)

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs : AttributeSet){
        val typedArray = context.theme.obtainStyledAttributes(attrs,R.styleable.EmotionView,0,0)
        colorBackground = typedArray.getColor(R.styleable.EmotionView_colorBackground,DEFAULT_BACKGROUND_COLOR)
        radius = typedArray.getDimension(R.styleable.EmotionView_radius, DEFAULT_RADIUS)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
        Log.d(TAG, "onMeasure: ")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(isInitConfig){
            initDraw(canvas)
        }
        drawPath(canvas)
        Log.d(TAG, "onDraw: ")
    }

    private fun initDraw(canvas: Canvas?){
        drawBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawBackground(canvas: Canvas?){
        paint.color = colorBackground
        paint.style = Paint.Style.FILL

        paint.color = Color.CYAN
        canvas?.drawRect(0f,0f, measuredWidth.toFloat(), measuredHeight.toFloat(),paint)
        paint.color = DEFAULT_BACKGROUND_COLOR
        Log.d(TAG, "drawBackground: " + radius)
        canvas?.drawCircle(measuredWidth.toFloat()/2, measuredHeight.toFloat()/2,radius,paint)
    }

    private fun drawEyes(canvas: Canvas?){
        paint.color = colorEyes
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DEFAULT_BORDER_WIDTH

        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas?.drawOval(leftEyeRect, paint)

        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas?.drawOval(rightEyeRect, paint)
    }

    private fun drawMouth(canvas: Canvas?){
        paint.color = colorEyes
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DEFAULT_BORDER_WIDTH

        //width = size
        //height = size
        mousePath.reset()
        mousePath.moveTo(size * 0.22f, size * 0.7f) // move to Point(x1 = 0.22 of size,x2 = 0.7 of size)
        //Draw quadratic (ham bac 2)
        mousePath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
        mousePath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
        canvas?.drawPath(mousePath,paint)
    }

    private fun drawPath(canvas: Canvas?){
        mousePath.reset()
        mousePath.moveTo(prePoint.first,prePoint.second)
        mousePath.lineTo(prePoint.first + size * 0.1f,prePoint.second + size* 0.1f)
        prePoint = Pair(prePoint.first + size * 0.1f, prePoint.second + size * 0.1f)

        canvas?.drawPath(mousePath,paint)
    }
}