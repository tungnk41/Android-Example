package com.example.component.component.multiImageLayout.layout

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun LayoutNImage(listUri: List<Uri>) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[0]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[1]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[2]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(listUri[3]),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(listUri[4]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Box(Modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f)))
                Text(
                    text = "+ ${listUri.size - 5}",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

        }
    }
}