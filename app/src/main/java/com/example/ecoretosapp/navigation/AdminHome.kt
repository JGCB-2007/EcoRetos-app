package com.example.ecoretosapp.navigation

/**
 * Contiene la configuración de navegación o vista principal
 * para el usuario administrador dentro de la aplicación.
 * Permite acceder a las funcionalidades disponibles para la gestión de retos.
 */

import androidx.compose.runtime.*
import com.example.ecoretosapp.ui.screens.AdminInicioDesign
import com.example.ecoretosapp.ui.screens.CrearRetoDesign
import com.example.ecoretosapp.ui.screens.PropuestasRetosAdminScreen
import com.example.ecoretosapp.ui.screens.RevisarEvidenciasAdminScreen
import com.example.ecoretosapp.ui.screens.CrearInsigniaScreen
data class RetoAdmin(
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
    var mensajeError by remember { mutableStateOf("") }

    var retos by remember {
        mutableStateOf(
            listOf(
                RetoAdmin(
                    nombre = "Reciclar botellas plásticas",
                    categoria = "Reciclaje",
                    puntos = "50",
                    descripcion = "Subir evidencia reciclando botellas dentro del campus."
                ),
                RetoAdmin(
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
            irPropuestas = { pantalla = "propuestas" },
            irRevisarEvidencias = { pantalla = "evidencias" },
            irCrearInsignia = { pantalla = "insignias" },
            onLogout = onLogout
        )
        "propuestas" -> PropuestasRetosAdminScreen(
            volver = {
                pantalla = "inicio"
            },
            onAceptar = { propuesta, puntos ->
                retos = retos + RetoAdmin(
                    nombre = propuesta.nombre,
                    categoria = propuesta.categoria,
                    puntos = puntos,
                    descripcion = propuesta.descripcion,
                    estado = "Activo"
                )
            },
            onRechazar = { propuesta ->
                // Por ahora solo se elimina de pendientes desde la pantalla de propuestas
            }
        )
        "evidencias" -> RevisarEvidenciasAdminScreen(
            volver = {
                pantalla = "inicio"
            },
            onAprobar = { evidencia ->
                // Luego aquí se sumarán puntos al estudiante desde backend
            },
            onRechazar = { evidencia ->
                // Luego aquí se marcará como rechazada desde backend
            }
        )
        "insignias" -> CrearInsigniaScreen(
            volver = {
                pantalla = "inicio"
            }
        )
        "crear" -> CrearRetoDesign(
            nombre = nombre,
            categoria = categoria,
            puntos = puntos,
            descripcion = descripcion,
            retos = retos,
            onNombreChange = { nombre = it },
            onCategoriaChange = { categoria = it },
            onPuntosChange = { puntos = it },
            onDescripcionChange = { descripcion = it },
            mensajeError = mensajeError,

            onEditar = { retoEditar ->
                nombre = retoEditar.nombre
                categoria = retoEditar.categoria
                puntos = retoEditar.puntos
                descripcion = retoEditar.descripcion

                retos = retos.filter { it != retoEditar }
                mensajeError = ""
            },
            onEliminar = { retoEliminar ->
                retos = retos.filter { it != retoEliminar }
            },

            volver = {
                pantalla = "inicio"
            },

            onGuardar = {
                if (nombre.isBlank() || categoria.isBlank() || puntos.isBlank() || descripcion.isBlank()) {
                    mensajeError = "Completa todos los campos antes de guardar"
                    return@CrearRetoDesign
                }
                if (puntos.toIntOrNull() == null) {
                    mensajeError = "Los puntos deben ser un número válido"
                    return@CrearRetoDesign
                }
                if (puntos.toInt() <= 0) {
                    mensajeError = "Los puntos deben ser mayores que 0"
                    return@CrearRetoDesign
                }
                if (nombre.trim().length < 3) {
                    mensajeError = "El nombre debe tener al menos 3 caracteres"
                    return@CrearRetoDesign
                }

                if (descripcion.trim().length < 10) {
                    mensajeError = "La descripción debe tener al menos 10 caracteres"
                    return@CrearRetoDesign
                }


                retos = retos + RetoAdmin(
                    nombre = nombre,
                    categoria = categoria,
                    puntos = puntos,
                    descripcion = descripcion
                )


                nombre = ""
                categoria = ""
                puntos = ""
                descripcion = ""
                mensajeError = ""
            }
        )
    }
}