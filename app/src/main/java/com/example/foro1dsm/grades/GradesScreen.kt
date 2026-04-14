package com.example.foro1dsm.grades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foro1dsm.navigation.AppRoutes

@Composable
fun GradesScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pantalla Ingreso de Notas")

        Button(
            onClick = { navController.navigate(AppRoutes.Result.route) }
        ) {
            Text("Ir a Resultado")
        }
    }
}