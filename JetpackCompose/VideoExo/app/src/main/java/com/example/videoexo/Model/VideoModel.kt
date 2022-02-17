package com.example.videoexo.Model

data class VideoModel(
    val id : Int,
    val mediaUrl : String,
    val thumbnail : String,
    val lastPlayPosition : Long = 0,
)