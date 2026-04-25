package com.example.foro1dsm.result

import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foro1dsm.navigation.AppRoutes

@Composable
fun ResultScreen(navController: NavController, average: Double) {
    val isPassed = average >= 6.0
    val statusText = if (isPassed) "¡Aprobado! 🥳" else "Reprobado 😔"
    val statusColor = if (isPassed) Color(0xFF4CAF50) else Color(0xFFF44336)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resultado del Estudiante",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Promedio obtenido:",
            fontSize = 18.sp
        )

        Text(
            text = String.format(Locale.getDefault(), "%.2f", average),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = statusText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = statusColor
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                navController.navigate(AppRoutes.Grades.route) {
                    popUpTo(AppRoutes.Grades.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar otras notas")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                navController.navigate(AppRoutes.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
