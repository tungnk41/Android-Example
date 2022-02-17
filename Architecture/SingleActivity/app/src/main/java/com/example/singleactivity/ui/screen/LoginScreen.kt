package com.example.singleactivity.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.singleactivity.ui.router.DestinationScreen
import com.example.singleactivity.ui.router.RouterManager
import com.example.singleactivity.ui.router_2.Screen

@Composable
fun LoginScreen() {
    Column(Modifier.fillMaxSize(), Arrangement.Center) {
        BasicText(text = "LoginScreen")
        Button(onClick = { RouterManager.navController?.navigate(Screen.HomeScreen.createRoute("123")) }) {}
    }
}