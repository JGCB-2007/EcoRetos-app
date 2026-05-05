package com.example.ecoretosapp.data.model

/**
 * Modelo de datos utilizado para enviar la solicitud de participación en un reto.
 * Contiene la información necesaria para que un usuario acepte un reto ecológico.
 */

data class AceptarRetoRequest(
    val idUsuario: Int
)