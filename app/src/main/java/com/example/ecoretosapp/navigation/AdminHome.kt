package com.example.ecoretosapp.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.data.model.Reto
import com.example.ecoretosapp.ui.screens.AdminInicioDesign
import com.example.ecoretosapp.ui.screens.CrearInsigniaScreen
import com.example.ecoretosapp.ui.screens.CrearRetoDesign
import com.example.ecoretosapp.ui.screens.PropuestasRetosAdminScreen
import com.example.ecoretosapp.ui.screens.RevisarEvidenciasAdminScreen
import com.example.ecoretosapp.viewmodel.AdminRetoViewModel

@Composable
fun AdminHome(
    onLogout: () -> Unit
) {
    var pantalla by remember { mutableStateOf("inicio") }

    val adminRetoViewModel: AdminRetoViewModel = viewModel()
    val retos by adminRetoViewModel.retos.collectAsState()
    val mensaje by adminRetoViewModel.mensaje.collectAsState()
    val error by adminRetoViewModel.error.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var dificultad by remember { mutableStateOf("") }
    var puntos by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipoValidacion by remember { mutableStateOf("") }
    var duracionHoras by remember { mutableStateOf("24") }
    var mensajeError by remember { mutableStateOf("") }

    var retoEditando by remember { mutableStateOf<Reto?>(null) }

    LaunchedEffect(Unit) {
        adminRetoViewModel.cargarRetos()
    }

    when (pantalla) {
        "inicio" -> AdminInicioDesign(
            irCrearReto = {
                limpiarFormulario(
                    onNombre = { nombre = it },
                    onDificultad = { dificultad = it },
                    onPuntos = { puntos = it },
                    onDescripcion = { descripcion = it },
                    onTipoValidacion = { tipoValidacion = it },
                    onDuracionHoras = { duracionHoras = it },
                    onMensajeError = { mensajeError = it },
                    onRetoEditando = { retoEditando = it }
                )
                adminRetoViewModel.cargarRetos()
                pantalla = "crear"
            },
            irPropuestas = { pantalla = "propuestas" },
            irRevisarEvidencias = { pantalla = "evidencias" },
            irCrearInsignia = { pantalla = "insignias" },
            onLogout = onLogout
        )

        "propuestas" -> PropuestasRetosAdminScreen(
            volver = {
                adminRetoViewModel.cargarRetos()
                pantalla = "inicio"
            }
        )

        "evidencias" -> RevisarEvidenciasAdminScreen(
            volver = {
                pantalla = "inicio"
            },
            onAprobar = { evidencia ->
                // Ya se maneja desde el ViewModel de evidencias
            },
            onRechazar = { evidencia ->
                // Ya se maneja desde el ViewModel de evidencias
            }
        )

        "insignias" -> CrearInsigniaScreen(
            volver = {
                pantalla = "inicio"
            }
        )

        "crear" -> CrearRetoDesign(
            nombre = nombre,
            categoria = dificultad,
            puntos = puntos,
            descripcion = descripcion,
            tipoValidacion = tipoValidacion,
            duracionHoras = duracionHoras,
            mensajeError = mensajeError.ifBlank {
                error ?: mensaje ?: ""
            },
            retos = retos,
            onNombreChange = { nombre = it },
            onCategoriaChange = { dificultad = it },
            onPuntosChange = { puntos = it },
            onDescripcionChange = { descripcion = it },
            onTipoValidacionChange = { tipoValidacion = it },
            onDuracionHorasChange = { duracionHoras = it },

            onGuardar = {
                val puntosInt = puntos.toIntOrNull()
                val duracionInt = duracionHoras.toIntOrNull()

                when {
                    nombre.isBlank() ||
                            dificultad.isBlank() ||
                            puntos.isBlank() ||
                            descripcion.isBlank() ||
                            tipoValidacion.isBlank() ||
                            duracionHoras.isBlank() -> {
                        mensajeError = "Completa todos los campos antes de guardar"
                    }

                    puntosInt == null -> {
                        mensajeError = "Los puntos deben ser un número válido"
                    }

                    puntosInt <= 0 -> {
                        mensajeError = "Los puntos deben ser mayores que 0"
                    }

                    duracionInt == null -> {
                        mensajeError = "La duración debe ser un número válido"
                    }

                    duracionInt <= 0 -> {
                        mensajeError = "La duración debe ser mayor que 0"
                    }

                    nombre.trim().length < 3 -> {
                        mensajeError = "El nombre debe tener al menos 3 caracteres"
                    }

                    descripcion.trim().length < 10 -> {
                        mensajeError = "La descripción debe tener al menos 10 caracteres"
                    }

                    else -> {
                        mensajeError = ""

                        if (retoEditando == null) {
                            adminRetoViewModel.crearReto(
                                titulo = nombre,
                                descripcion = descripcion,
                                puntos = puntosInt,
                                dificultad = dificultad,
                                tipoValidacion = tipoValidacion,
                                duracionHoras = duracionInt,
                                creadoPor = 1
                            )
                        } else {
                            adminRetoViewModel.editarReto(
                                idReto = retoEditando!!.idReto,
                                titulo = nombre,
                                descripcion = descripcion,
                                puntos = puntosInt,
                                dificultad = dificultad,
                                tipoValidacion = tipoValidacion,
                                duracionHoras = duracionInt
                            )
                        }

                        limpiarFormulario(
                            onNombre = { nombre = it },
                            onDificultad = { dificultad = it },
                            onPuntos = { puntos = it },
                            onDescripcion = { descripcion = it },
                            onTipoValidacion = { tipoValidacion = it },
                            onDuracionHoras = { duracionHoras = it },
                            onMensajeError = { mensajeError = it },
                            onRetoEditando = { retoEditando = it }
                        )
                    }
                }
            },

            onEditar = { reto ->
                retoEditando = reto
                nombre = reto.titulo
                dificultad = reto.dificultad
                puntos = reto.puntos.toString()
                descripcion = reto.descripcion
                tipoValidacion = reto.tipoValidacion
                duracionHoras = reto.duracionHoras?.toString() ?: "24"
                mensajeError = ""
            },

            onEliminar = { reto ->
                adminRetoViewModel.eliminarReto(reto.idReto)
            },

            volver = {
                pantalla = "inicio"
            }
        )
    }
}

private fun limpiarFormulario(
    onNombre: (String) -> Unit,
    onDificultad: (String) -> Unit,
    onPuntos: (String) -> Unit,
    onDescripcion: (String) -> Unit,
    onTipoValidacion: (String) -> Unit,
    onDuracionHoras: (String) -> Unit,
    onMensajeError: (String) -> Unit,
    onRetoEditando: (Reto?) -> Unit
) {
    onNombre("")
    onDificultad("")
    onPuntos("")
    onDescripcion("")
    onTipoValidacion("")
    onDuracionHoras("24")
    onMensajeError("")
    onRetoEditando(null)
}