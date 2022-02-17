package com.example.component.component.multiImageLayout

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.component.component.DialogTakePicture

@Composable
fun PickUpListImage(modifier: Modifier = Modifier, onCompleted: (List<Uri>?) -> Unit) {
    val context = LocalContext.current

    val launcherGetListUri = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { listUri: List<Uri>? ->

        onCompleted(listUri)
    }

    val launcherRequestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launcherGetListUri.launch("image/*")
        } else {
            Log.d("EditUserProfileScreen", "PERMISSION DENIED")
        }
    }
    Button(
        modifier = modifier,
        onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                launcherGetListUri.launch("image/*")
            }else{
                launcherRequestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    ){
        BasicText(text = "Pick")
    }
}