package com.example.ecoretosapp.data.model

/**
 * Modelo de datos que representa la respuesta del servidor tras un intento de login.
 * Incluye información del usuario autenticado y posibles datos de sesión.
 */

data class LoginResponse(
    val idUsuario: Int,
    val cif: String,
    val nombreCompleto: String,
    val rol: String,
    val mensaje: String
)