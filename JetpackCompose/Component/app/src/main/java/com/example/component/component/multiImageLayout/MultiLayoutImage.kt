package com.example.component.component.multiImageLayout

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.component.component.multiImageLayout.layout.*

@Composable
fun MultiLayoutImage() {
    var listUri by remember { mutableStateOf(listOf<Uri>()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        PickUpListImage(onCompleted = {
            it?.let {
                listUri = it
                Log.d(ContentValues.TAG, "PickUpImage: ${it} ${it.size}")
            }
        })

        when (listUri.size) {
            0 -> {}
            1 -> {
                Layout1Image(listUri = listUri)
            }
            2 -> {
                Layout2Image(listUri = listUri)
            }
            3 -> {
                Layout3Image(listUri = listUri)
            }
            4 -> {
                Layout4Image(listUri = listUri)
            }
            5 -> {
                Layout5Image(listUri = listUri)
            }
            else -> {
                LayoutNImage(listUri = listUri)
            }
        }
    }
}