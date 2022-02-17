package com.example.navigation.Screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ProfileOption(navController: NavController,name: String,age :Int) {
    Button(onClick = { navController.navigate("home") }) {
        Text(text = "${name} +  ${age+10}")
    }
}