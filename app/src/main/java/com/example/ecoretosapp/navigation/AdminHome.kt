package com.example.ecoretosapp.navigation

import androidx.compose.runtime.*
import com.example.ecoretosapp.ui.screens.AdminInicioDesign
import com.example.ecoretosapp.ui.screens.CrearRetoDesign

data class Reto(
    val nombre: String,
    val categoria: String,
    val puntos: String,
    val descripcion: String,
    val estado: String = "Activo"
)

@Composable
fun AdminHome(
    onLogout: () -> Unit
) {
    var pantalla by remember { mutableStateOf("inicio") }

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var puntos by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var retos by remember {
        mutableStateOf(
            listOf(
                Reto(
                    nombre = "Reciclar botellas plásticas",
                    categoria = "Reciclaje",
                    puntos = "50",
                    descripcion = "Subir evidencia reciclando botellas dentro del campus."
                ),
                Reto(
                    nombre = "Apagar luces innecesarias",
                    categoria = "Energía",
                    puntos = "35",
                    descripcion = "Registrar una acción de ahorro energético."
                )
            )
        )
    }

    when (pantalla) {
        "inicio" -> AdminInicioDesign(
            irCrearReto = { pantalla = "crear" },
            onLogout = onLogout
        )

        "crear" -> CrearRetoDesign(
            nombre = nombre,
            categoria = categoria,
            puntos = puntos,
            descripcion = descripcion,
            retos = retos,
            onNombreChange = { nombre = it },
            onCategoriaChange = { categoria = it },
            onPuntosChange = { nuevo ->
                if (nuevo.all { it.isDigit() }) {
                    puntos = nuevo
                }
            },
            onDescripcionChange = { descripcion = it },
            volver = { pantalla = "inicio" },
            onGuardar = {
                if (
                    nombre.isNotBlank() &&
                    categoria.isNotBlank() &&
                    puntos.isNotBlank() &&
                    descripcion.isNotBlank()
                ) {
                    retos = listOf(
                        Reto(
                            nombre = nombre,
                            categoria = categoria,
                            puntos = puntos,
                            descripcion = descripcion
                        )
                    ) + retos

                    nombre = ""
                    categoria = ""
                    puntos = ""
                    descripcion = ""
                }
            }
        )
    }
}