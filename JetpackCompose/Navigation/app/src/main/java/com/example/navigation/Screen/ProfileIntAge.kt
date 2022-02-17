package com.example.navigation.Screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController

@Composable
fun ProfileIntAge(navController: NavController, age: Int) {

    Button(onClick = { navController.navigate("home") }) {
        Text(text = "${age+5}")
    }
}