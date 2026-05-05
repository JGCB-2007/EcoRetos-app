package com.example.ecoretosapp.data.model

/**
 * Modelo de datos que representa la respuesta del servidor al registrar
 * la participación de un usuario en un reto.
 * Permite confirmar si la operación fue exitosa.
 */

data class ParticipacionResponse(
    val idParticipacion: Int,
    val idUsuario: Int,
    val idReto: Int,
    val tituloReto: String,
    val estado: String,
    val fechaAceptacion: String,
    val mensaje: String
)