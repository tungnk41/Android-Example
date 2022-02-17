package com.example.singleactivity.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.singleactivity.ui.router.DestinationScreen
import com.example.singleactivity.ui.router.RouterManager

@Composable
fun SplashScreen() {
    Column(Modifier.fillMaxSize(),Arrangement.Center) {
        BasicText(text = "SplashScreen")
        Button(onClick = { RouterManager.navController?.navigate(DestinationScreen.LoginScreen()) }) {}
    }
}