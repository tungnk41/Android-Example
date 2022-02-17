package com.example.singleactivity.ui.router

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.singleactivity.ui.router.graph.GraphName
import com.example.singleactivity.ui.router.graph.HomeGraph
import com.example.singleactivity.ui.router.graph.LoginGraph

@Composable
fun Router() {
    val navController = rememberNavController()
    RouterManager.navController = navController
    navController.currentDestination?.let { }
    NavHost(navController, startDestination = GraphName.LOGIN_GRAPH) {
        LoginGraph()
        HomeGraph()
    }
}