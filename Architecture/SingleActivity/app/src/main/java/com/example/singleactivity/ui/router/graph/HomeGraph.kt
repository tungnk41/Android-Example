package com.example.singleactivity.ui.router.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.singleactivity.ui.router.DestinationScreen
import com.example.singleactivity.ui.router_2.Screen
import com.example.singleactivity.ui.screen.HomeScreen

fun NavGraphBuilder.HomeGraph() {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = GraphName.HOME_GRAPH
    ) {
        composable(route = Screen.HomeScreen.route) {
            val id = it.arguments?.getString("id")
            HomeScreen(id = id ?: "")
        }
    }
}