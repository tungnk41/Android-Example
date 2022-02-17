package com.example.component.component

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat

@Composable
fun PickUpImage(modifier: Modifier = Modifier, onCompleted: (Bitmap?) -> Unit) {
    val context = LocalContext.current
    var bitmap: Bitmap? = null
    var isTakePictureFromCamera by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var listUri by remember {mutableStateOf(mutableListOf<Uri>())}
    fun dismiss() {
        isShowDialog = false
    }

    val launcherGetImage = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
        }
        onCompleted(bitmap)
    }

    val launcherGetListUri = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { Uris: List<Uri>? ->
        
        Uris?.let { 
            listUri = Uris.toMutableList()
        }
        Log.d(TAG, "PickUpImage: ${listUri}")
    }
    
    val launcherTakePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            onCompleted(it)
        }
    val launcherRequestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isTakePictureFromCamera) {
                launcherTakePicture.launch(null)
            } else {
                launcherGetImage.launch("image/*")
            }
        } else {
            Log.d("EditUserProfileScreen", "PERMISSION DENIED")
        }
    }
    Button(
        modifier = modifier,
        onClick = {
            isShowDialog = true
        }
    ){
        BasicText(text = "Pick")
    }
    if (isShowDialog) {
        DialogTakePicture(
            onCompleted = {
                isTakePictureFromCamera = it
                var requestPermission =
                    if (isTakePictureFromCamera)
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    else
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                when {
                    requestPermission == PackageManager.PERMISSION_GRANTED -> {
                        if (isTakePictureFromCamera) launcherTakePicture.launch(null) else launcherGetImage.launch(
                            "image/*"
                        )
                    }
                    else -> {
                        if (isTakePictureFromCamera) launcherRequestPermission.launch(Manifest.permission.CAMERA) else launcherRequestPermission.launch(
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    }
                }
                dismiss()
            },
            onDismiss = {
                dismiss()
            })
    }
}

@Composable
fun DialogTakePicture(onDismiss: ()->Unit, onCompleted:(Boolean)->Unit){
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(Modifier.fillMaxWidth()) {
            Button(onClick = {
                onDismiss()
                onCompleted(true)
            }) {
                BasicText(text = "Take Picture from camera")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                onDismiss()
                onCompleted(false)
            }) {
                BasicText(text = "Take Picture From Local")
            }

        }
    }
}