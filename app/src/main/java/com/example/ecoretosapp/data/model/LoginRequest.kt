package com.example.ecoretosapp.data.model

/**
 * Proporciona la configuración y la instancia de Retrofit utilizada en la aplicación.
 * Se encarga de establecer la URL base del servidor, el convertidor JSON
 * y el cliente HTTP para realizar las peticiones a la API.
 */

data class LoginRequest(
    val cif: String,
    val password: String
)