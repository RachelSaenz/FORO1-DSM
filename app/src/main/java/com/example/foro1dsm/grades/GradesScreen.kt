package com.example.foro1dsm.grades

import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foro1dsm.navigation.AppRoutes

@Composable
fun GradesScreen(navController: NavController) {
    var note1 by remember { mutableStateOf("") }
    var note2 by remember { mutableStateOf("") }
    var note3 by remember { mutableStateOf("") }
    var average by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingreso de Notas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = note1,
            onValueChange = { note1 = it },
            label = { Text("Nota 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note2,
            onValueChange = { note2 = it },
            label = { Text("Nota 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note3,
            onValueChange = { note3 = it },
            label = { Text("Nota 3") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val n1 = note1.toDoubleOrNull() ?: 0.0
                val n2 = note2.toDoubleOrNull() ?: 0.0
                val n3 = note3.toDoubleOrNull() ?: 0.0
                average = (n1 + n2 + n3) / 3
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Promedio")
        }

        average?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Promedio: ${String.format(Locale.getDefault(), "%.2f", it)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(AppRoutes.Result.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Resultado")
        }
    }
}
