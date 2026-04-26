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

@Composable
fun GradesScreen(navController: NavController) {
    var note1 by remember { mutableStateOf("") }
    var note2 by remember { mutableStateOf("") }
    var note3 by remember { mutableStateOf("") }

    var error1 by remember { mutableStateOf<String?>(null) }
    var error2 by remember { mutableStateOf<String?>(null) }
    var error3 by remember { mutableStateOf<String?>(null) }

    var average by remember { mutableStateOf<Double?>(null) }

    fun validateNote(value: String): String? {
        if (value.isBlank()) return "Campo obligatorio"
        val number = value.toDoubleOrNull() ?: return "Debe ser un número válido"
        if (number < 0 || number > 10) return "Debe estar entre 0 y 10"
        return null
    }

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
            isError = error1 != null,
            supportingText = { error1?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note2,
            onValueChange = { note2 = it },
            label = { Text("Nota 2") },
            isError = error2 != null,
            supportingText = { error2?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note3,
            onValueChange = { note3 = it },
            label = { Text("Nota 3") },
            isError = error3 != null,
            supportingText = { error3?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                error1 = validateNote(note1)
                error2 = validateNote(note2)
                error3 = validateNote(note3)

                if (error1 == null && error2 == null && error3 == null) {
                    val n1 = note1.toDouble()
                    val n2 = note2.toDouble()
                    val n3 = note3.toDouble()
                    average = (n1 + n2 + n3) / 3
                } else {
                    average = null
                }
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
                val formattedAverage = String.format(Locale.US, "%.2f", average ?: 0.0)
                navController.navigate("result/$formattedAverage")
            },
            enabled = average != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Resultado")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }

        }
    }
