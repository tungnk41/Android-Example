package com.example.component.component.multiImageLayout.layout

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun Layout3Image(listUri : List<Uri>) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(2f),
            painter = rememberImagePainter(listUri[0]),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[1]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[2]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}