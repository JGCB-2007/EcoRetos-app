package com.example.ecoretosapp.ui.screens

/**
 * Pantalla encargada de mostrar la lista de retos ecológicos disponibles.
 * Permite al usuario visualizar, seleccionar o participar en los retos
 * registrados en la aplicación.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun RetosScreen(
    idUsuario: Int,
    onEnviarEvidencia: (com.example.ecoretosapp.data.model.Reto) -> Unit,
    viewModel: RetoViewModel = viewModel()
){
    val retos by viewModel.retos.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val retosAceptados by viewModel.retosAceptados.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarRetos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFECFDF5), Color.White, Color(0xFFF7FEE7))
                )
            )
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Mis retos aceptados",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A)
                    )

                    Text(
                        text = "Aquí aparecen los retos que ya aceptaste.",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            if (mensaje != null) {
                item {
                    Text(
                        text = mensaje ?: "",
                        color = Color(0xFF047857),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (error != null) {
                item {
                    Text(
                        text = error ?: "",
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            val retosAceptadosLista = retos.filter { reto ->
                retosAceptados.contains(reto.idReto)
            }

            if (retosAceptadosLista.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {
                        Text(
                            text = "Aún no has aceptado ningún reto.",
                            modifier = Modifier.padding(20.dp),
                            fontSize = 16.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            items(retosAceptadosLista) { reto ->
                val aceptado = true

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFD1FAE5), RoundedCornerShape(18.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (aceptado) "✅" else "♻️",
                                    fontSize = 30.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = reto.titulo,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = "Reto • ${reto.dificultad}",
                                            fontSize = 13.sp,
                                            color = Color(0xFF64748B)
                                        )
                                    }

                                    Text(
                                        text = if (aceptado) "Aceptado" else "+${reto.puntos}",
                                        modifier = Modifier
                                            .background(
                                                if (aceptado) Color(0xFFD1FAE5) else Color(0xFFDCFCE7),
                                                RoundedCornerShape(50.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF15803D)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = reto.descripcion,
                                    fontSize = 14.sp,
                                    color = Color(0xFF475569)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (aceptado) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.cancelarRetoApi(
                                            idReto = reto.idReto,
                                            idUsuario = idUsuario
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(18.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEF4444)
                                    )
                                ) {
                                    Text(
                                        text = "Cancelar",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Button(
                                    onClick = {
                                        onEnviarEvidencia(reto)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(18.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF0EA5E9)
                                    )
                                ) {
                                    Text(
                                        text = "Completar reto",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(0xFF10B981),
                                                Color(0xFF84CC16)
                                            )
                                        ),
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .clickable {
                                        viewModel.aceptarReto(
                                            idReto = reto.idReto,
                                            idUsuario = idUsuario
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Aceptar reto",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}