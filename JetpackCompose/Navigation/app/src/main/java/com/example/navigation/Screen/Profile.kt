package com.example.navigation.Screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.navigation.Model.User

@Composable
fun Profile(navController: NavController,user : User) {
    Button(onClick = { navController.navigate("home") }) {
        Text(text = "${user.name} + ${user.age + 10}")
    }
}