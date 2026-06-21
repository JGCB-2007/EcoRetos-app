package com.example.ecoretosapp.ui.screens

/**
 * Contenedor principal de las pantallas del estudiante.
 * Organiza la navegación y visualización de las secciones disponibles
 * para el rol de estudiante.
 */

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.ecoretosapp.data.model.Reto

@Composable
fun MainEstudianteScreen(idUsuario: Int, navController: NavController, onLogout: () -> Unit) {

    var selectedItem by remember { mutableStateOf(0) }
    var retoParaEvidencia by remember { mutableStateOf<Reto?>(null) }

    val items = listOf("Inicio", "Retos", "Proponer", "Ranking", "Perfil")

    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.CheckCircle,
        Icons.Default.AddCircle,
        Icons.Default.Star,
        Icons.Default.Person
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) }
                    )
                }
            }
        }
    ) { padding ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(padding)
        ) {
            if (retoParaEvidencia != null) {
                EnviarEvidenciaScreen(
                    idReto = retoParaEvidencia!!.idReto,
                    nombreReto = retoParaEvidencia!!.titulo,
                    onEnviar = {
                        retoParaEvidencia = null
                        selectedItem = 1
                    },
                    volver = {
                        retoParaEvidencia = null
                        selectedItem = 1
                    }
                )
            } else {
                when (selectedItem) {
                0 -> HomeEstudianteScreen(idUsuario = idUsuario, navController = navController)

                    1 -> RetosScreen(
                        idUsuario = idUsuario,
                        onEnviarEvidencia = { reto ->
                            retoParaEvidencia = reto
                        }
                    )

                2 -> CrearRetoEstudianteScreen(
                    volver = {
                        selectedItem = 0
                    }
                )

                3 -> RankingScreen()

                4 -> PerfilEstudianteScreen(
                    onLogout = onLogout
                )
            }
        }
    }
}
}