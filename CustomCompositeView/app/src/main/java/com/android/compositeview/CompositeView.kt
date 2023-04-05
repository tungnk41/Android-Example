package com.android.compositeview

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout


class CompositeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    var onClicked: (() -> Unit)? = null

    init {
        inflate(context,R.layout.view_item_composite, this)

        val button: Button = findViewById(R.id.button)
        val progress: ProgressBar = findViewById(R.id.progressBar)


        button.setOnClickListener {
            onClicked?.invoke()
        }
    }

}