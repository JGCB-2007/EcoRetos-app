package com.example.ecoretosapp.data.model

data class RevisionPropuestaRequest(
    val idAdministrador: Int,
    val puntos: Int? = null,
    val dificultad: String? = null,
    val tipoValidacion: String? = null,
    val duracionHoras: Int? = null,
    val observacionAdmin: String? = null
)