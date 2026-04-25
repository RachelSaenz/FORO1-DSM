package com.example.foro1dsm.navigation

sealed class AppRoutes(val route: String) {
    object Login : AppRoutes("login")
    object Register : AppRoutes("register")
    object Welcome : AppRoutes("welcome/{email}")
    object Grades : AppRoutes("grades")
    object Result : AppRoutes("result/{average}")
}