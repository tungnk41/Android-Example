package com.example.singleactivity.ui.router_2

sealed class Screen(val route: String){
//    object HomeScreen: Screen("HomeScreen/{id}"){
//        fun createRoute(id: String) = "HomeScreen/$id"
//    }
    object HomeScreen: Screen("HomeScreen?id={id}"){
        fun createRoute(id: String) = "HomeScreen?id=$id"
    }
    object LoginScreen: Screen("LoginScreen")
    object SplashScreen: Screen("SplashScreen")
}