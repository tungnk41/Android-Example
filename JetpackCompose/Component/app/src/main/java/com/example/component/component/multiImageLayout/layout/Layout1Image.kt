package com.example.component.component.multiImageLayout.layout

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@Composable
fun Layout1Image(listUri : List<Uri>) {
    Image(
        modifier = Modifier
            .aspectRatio(1.5f),
        painter = rememberImagePainter(listUri[0]),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}