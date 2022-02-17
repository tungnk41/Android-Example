package com.example.singleactivity.ui.router.graph


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.singleactivity.ui.router.DestinationScreen
import com.example.singleactivity.ui.screen.LoginScreen
import com.example.singleactivity.ui.screen.SplashScreen

fun NavGraphBuilder.LoginGraph() {
    navigation(
        startDestination = DestinationScreen.SplashScreen(),
        route = GraphName.LOGIN_GRAPH
    ) {
        composable(DestinationScreen.SplashScreen()) {
            SplashScreen()
        }
        composable(DestinationScreen.LoginScreen()) {
            LoginScreen()
        }
    }
}