package com.example.foro1dsm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foro1dsm.grades.GradesScreen
import com.example.foro1dsm.login.LoginScreen
import com.example.foro1dsm.result.ResultScreen
import com.example.foro1dsm.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {
        composable(AppRoutes.Login.route) {
            LoginScreen(navController)
        }

        composable(AppRoutes.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(AppRoutes.Grades.route) {
            GradesScreen(navController)
        }

        composable(AppRoutes.Result.route) {
            ResultScreen(navController)
        }
    }
}