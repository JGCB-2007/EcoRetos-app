package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun HomeEstudianteScreen(
    idUsuario: Int,
    viewModel: RetoViewModel = viewModel()
) {
    val retos by viewModel.retos.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarRetos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Retos disponibles",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (mensaje != null) {
            Text(
                text = mensaje ?: "",
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(retos) { reto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = reto.titulo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = reto.descripcion)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Puntos: ${reto.puntos}")
                        Text("Dificultad: ${reto.dificultad}")

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.aceptarReto(
                                    idReto = reto.idReto,
                                    idUsuario = idUsuario
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Aceptar reto")
                        }
                    }
                }
            }
        }
    }
}