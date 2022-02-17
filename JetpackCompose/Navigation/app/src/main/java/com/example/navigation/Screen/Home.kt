package com.example.navigation.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.navigation.Model.User
import com.google.gson.Gson

@Composable
fun Home(navController: NavController) {
    val user = User("name", 10)
    val userJson = Gson().toJson(user)
    val name = "name_1"
    var  age = 100
    Column() {
        Button(onClick = { navController.navigate("profile_1/$userJson") }) {
            Text(text = "Profile User")
        }
        Button(onClick = { navController.navigate("profile_2/${name}") }) {
            Text(text = "Profile String Name")
        }
        Button(onClick = { navController.navigate("profile_3/${age}") }) {
            Text(text = "Profile Int Age")
        }
        Button(onClick = { navController.navigate("profile_4?name=${name}") }) {
            Text(text = "Profile Option Name")
        }
        Button(onClick = { navController.navigate("profile_4?name=${name}&age=${age}") }) {
            Text(text = "Profile Option Name+Age")
        }
    }

}