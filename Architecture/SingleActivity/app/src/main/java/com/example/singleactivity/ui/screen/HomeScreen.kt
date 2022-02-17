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
import com.example.singleactivity.viewmodel.MainViewModel

@Composable
fun HomeScreen(id: String,mainViewModel: MainViewModel = MainViewModel()) {
    Column(Modifier.fillMaxSize(), Arrangement.Center) {
        BasicText(text = "HomeScreen ${id}")
        Button(onClick = {
            RouterManager.navController?.navigate(DestinationScreen.SplashScreen())
            mainViewModel.getCurrency()
        }) {}
    }
}