package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigation.Model.User
import com.example.navigation.Screen.*
import com.google.gson.Gson

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {

        composable(
            "profile_1/{user}",
        )
        { backStackEntry ->
            backStackEntry?.arguments?.getString("user")?.let { json ->
                val user = Gson().fromJson(json, User::class.java)
                Profile(navController, user = user)
            }
        }

        composable(
            "profile_2/{name}",
        )
        { backStackEntry ->
            backStackEntry?.arguments?.getString("name")?.let { name ->
                ProfileStringName(navController, name)
            }
        }

        composable(
            "profile_3/{age}",
            arguments = listOf(
                navArgument("age") { type = NavType.IntType })
        )
        { backStackEntry ->
            val age = backStackEntry?.arguments?.getInt("age")
            ProfileIntAge(navController, age ?: 0)
        }

        composable(
            "profile_4?name={name}&age={age}",
            arguments = listOf(
                navArgument("name") {  defaultValue = "name_10" },
                navArgument("age") { defaultValue= 0 ; type = NavType.IntType })
        )
        { backStackEntry ->
            val name = backStackEntry?.arguments?.getString("name")
            val age = backStackEntry?.arguments?.getInt("age")
            ProfileOption(navController, name ?: "", age ?: 0)
        }

        composable("home")
        { Home(navController) }

    }
}