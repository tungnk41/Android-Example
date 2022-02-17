package com.example.snippet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.jar.Manifest

@ExperimentalPermissionsApi
@Composable
fun PermissionRequest() {
    val permissionState = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA,
    ))

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver{_, event ->
            if(event == Lifecycle.Event.ON_START){
                permissionState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    })

    Column(Modifier.fillMaxSize()) {
        permissionState.permissions.forEach {
            when(it.permission){
                android.Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    when{
                        it.hasPermission ->{
                            Text(text = "Permission accept")
                        }
                        it.shouldShowRationale ->{
                            Text(text = "Permission need to access data")
                        }
                        (!it.hasPermission && !it.shouldShowRationale) ->{
                            Text(text = "Permission denied")
                        }
                    }

                }
                android.Manifest.permission.CAMERA -> {

                }
            }
        }
    }
}