package com.example.foro1dsm.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foro1dsm.login.AuthEvent
import com.example.foro1dsm.login.AuthViewModel
import com.example.foro1dsm.navigation.AppRoutes

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    val name by viewModel.registerName.collectAsStateWithLifecycle()
    val email by viewModel.registerEmail.collectAsStateWithLifecycle()
    val password by viewModel.registerPassword.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.registerConfirmPassword.collectAsStateWithLifecycle()

    val nameError by viewModel.registerNameError.collectAsStateWithLifecycle()
    val emailError by viewModel.registerEmailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.registerPasswordError.collectAsStateWithLifecycle()
    val confirmPasswordError by viewModel.registerConfirmPasswordError.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.registerEvents.collect { event ->
            when (event) {
                is AuthEvent.NavigateToWelcome -> {
                    navController.navigate(AppRoutes.Welcome.route) {
                        popUpTo(AppRoutes.Login.route) { inclusive = true }
                    }
                }
                is AuthEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = viewModel::onRegisterNameChange,
                label = { Text("Nombre") },
                isError = nameError != null,
                supportingText = { nameError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = viewModel::onRegisterEmailChange,
                label = { Text("Correo electrónico") },
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = viewModel::onRegisterPasswordChange,
                label = { Text("Contraseña") },
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it) } },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                                       else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility
                                          else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña"
                                                 else "Mostrar contraseña"
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = viewModel::onRegisterConfirmPasswordChange,
                label = { Text("Confirmar contraseña") },
                isError = confirmPasswordError != null,
                supportingText = { confirmPasswordError?.let { Text(it) } },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
                                       else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility
                                          else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Ocultar contraseña"
                                                 else "Mostrar contraseña"
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::validateAndRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigateUp() }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}
