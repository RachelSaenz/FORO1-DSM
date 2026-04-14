package com.example.foro1dsm.navigation

sealed class AppRoutes(val route: String) {
    object Login : AppRoutes("login")
    object Welcome : AppRoutes("welcome")
    object Grades : AppRoutes("grades")
    object Result : AppRoutes("result")
}