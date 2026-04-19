package com.example.foro1dsm.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.foro1dsm.data.UserRepository
import com.example.foro1dsm.util.hashSHA256
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class AuthEvent {
    object NavigateToWelcome : AuthEvent()
    data class ShowSnackbar(val message: String) : AuthEvent()
}

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    // ——— Login fields ———
    private val _loginEmail = MutableStateFlow("")
    val loginEmail = _loginEmail.asStateFlow()

    private val _loginPassword = MutableStateFlow("")
    val loginPassword = _loginPassword.asStateFlow()

    private val _loginEmailError = MutableStateFlow<String?>(null)
    val loginEmailError = _loginEmailError.asStateFlow()

    private val _loginPasswordError = MutableStateFlow<String?>(null)
    val loginPasswordError = _loginPasswordError.asStateFlow()

    // ——— Register fields ———
    private val _registerName = MutableStateFlow("")
    val registerName = _registerName.asStateFlow()

    private val _registerEmail = MutableStateFlow("")
    val registerEmail = _registerEmail.asStateFlow()

    private val _registerPassword = MutableStateFlow("")
    val registerPassword = _registerPassword.asStateFlow()

    private val _registerConfirmPassword = MutableStateFlow("")
    val registerConfirmPassword = _registerConfirmPassword.asStateFlow()

    private val _registerNameError = MutableStateFlow<String?>(null)
    val registerNameError = _registerNameError.asStateFlow()

    private val _registerEmailError = MutableStateFlow<String?>(null)
    val registerEmailError = _registerEmailError.asStateFlow()

    private val _registerPasswordError = MutableStateFlow<String?>(null)
    val registerPasswordError = _registerPasswordError.asStateFlow()

    private val _registerConfirmPasswordError = MutableStateFlow<String?>(null)
    val registerConfirmPasswordError = _registerConfirmPasswordError.asStateFlow()

    // ——— Separate event channels (avoids cross-screen event leakage) ———
    private val _loginEvents = Channel<AuthEvent>(Channel.BUFFERED)
    val loginEvents = _loginEvents.receiveAsFlow()

    private val _registerEvents = Channel<AuthEvent>(Channel.BUFFERED)
    val registerEvents = _registerEvents.receiveAsFlow()

    // ——— Login field updates ———
    fun onLoginEmailChange(value: String) { _loginEmail.value = value }
    fun onLoginPasswordChange(value: String) { _loginPassword.value = value }

    // ——— Register field updates ———
    fun onRegisterNameChange(value: String) { _registerName.value = value }
    fun onRegisterEmailChange(value: String) { _registerEmail.value = value }
    fun onRegisterPasswordChange(value: String) { _registerPassword.value = value }
    fun onRegisterConfirmPasswordChange(value: String) { _registerConfirmPassword.value = value }

    // ——— Shared validators ———
    private fun validateEmail(email: String): String? = when {
        email.isEmpty() -> "El correo es obligatorio"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de correo inválido"
        else -> null
    }

    private fun validatePassword(password: String): String? = when {
        password.isEmpty() -> "La contraseña es obligatoria"
        password.length < 8 -> "Mínimo 8 caracteres"
        !password.any { it.isUpperCase() } -> "Debe contener al menos una mayúscula"
        !password.any { it.isLowerCase() } -> "Debe contener al menos una minúscula"
        !password.any { it.isDigit() } -> "Debe contener al menos un número"
        !password.any { it in "!@#\$%^&*" } -> "Debe contener al menos un símbolo"
        else -> null
    }

    // ——— Login: validate then authenticate ———
    fun validateAndLogin() {
        val emailError = validateEmail(_loginEmail.value)
        val passwordError = validatePassword(_loginPassword.value)

        _loginEmailError.value = emailError
        _loginPasswordError.value = passwordError

        if (emailError != null || passwordError != null) return

        viewModelScope.launch {
            val user = repository.getUserByEmail(_loginEmail.value.trim())
            if (user != null && user.password == hashSHA256(_loginPassword.value)) {
                _loginEvents.send(AuthEvent.NavigateToWelcome)
            } else {
                _loginEvents.send(AuthEvent.ShowSnackbar("Correo o contraseña incorrectos"))
            }
        }
    }

    // ——— Register: validate then create account ———
    fun validateAndRegister() {
        val nameError = if (_registerName.value.isEmpty()) "El nombre es obligatorio" else null
        val emailError = validateEmail(_registerEmail.value)
        val passwordError = validatePassword(_registerPassword.value)
        val confirmError = when {
            _registerConfirmPassword.value.isEmpty() -> "Confirma tu contraseña"
            _registerConfirmPassword.value != _registerPassword.value -> "Las contraseñas no coinciden"
            else -> null
        }

        _registerNameError.value = nameError
        _registerEmailError.value = emailError
        _registerPasswordError.value = passwordError
        _registerConfirmPasswordError.value = confirmError

        if (nameError != null || emailError != null || passwordError != null || confirmError != null) return

        viewModelScope.launch {
            if (repository.isEmailTaken(_registerEmail.value.trim())) {
                _registerEvents.send(AuthEvent.ShowSnackbar("Este correo ya está registrado"))
                return@launch
            }
            repository.insertUser(
                name = _registerName.value.trim(),
                email = _registerEmail.value.trim(),
                password = _registerPassword.value
            )
            _registerEvents.send(AuthEvent.NavigateToWelcome)
        }
    }
}

class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
