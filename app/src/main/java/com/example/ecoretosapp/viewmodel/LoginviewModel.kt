package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.LoginRequest
import com.example.ecoretosapp.data.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(cif: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginUiState.Loading

                val response = RetrofitClient.apiService.login(
                    LoginRequest(cif = cif, password = password)
                )

                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = LoginUiState.Success(response.body()!!)
                } else {
                    _loginState.value = LoginUiState.Error("Credenciales incorrectas")
                }

            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val usuario: LoginResponse) : LoginUiState()
    data class Error(val mensaje: String) : LoginUiState()
}