package com.example.foro1dsm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foro1dsm.login.AuthViewModel
import com.example.foro1dsm.login.AuthViewModelFactory
import com.example.foro1dsm.data.UserDatabase
import com.example.foro1dsm.data.UserRepository
import com.example.foro1dsm.grades.GradesScreen
import com.example.foro1dsm.login.LoginScreen
import com.example.foro1dsm.register.RegisterScreen
import com.example.foro1dsm.result.ResultScreen
import com.example.foro1dsm.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val factory = remember {
        AuthViewModelFactory(
            UserRepository(UserDatabase.getInstance(context).userDao())
        )
    }
    val authViewModel: AuthViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {
        composable(AppRoutes.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(AppRoutes.Register.route) {
            RegisterScreen(navController, authViewModel)
        }

        composable(AppRoutes.Welcome.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            WelcomeScreen(
                navController = navController,
                email = email
            )
        }

        composable(AppRoutes.Grades.route) {
            GradesScreen(navController)
        }

        composable(
            route = AppRoutes.Result.route,
            arguments = listOf(navArgument("average") { type = NavType.FloatType })
        ) { backStackEntry ->
            val average = backStackEntry.arguments?.getFloat("average")?.toDouble() ?: 0.0
            ResultScreen(navController, average)
        }
    }
}
