package com.example.firerealtime.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id : String = "",
    val content: String = "",
    val author: String = "",
    val timestamp: Long = 0L
) : Parcelable