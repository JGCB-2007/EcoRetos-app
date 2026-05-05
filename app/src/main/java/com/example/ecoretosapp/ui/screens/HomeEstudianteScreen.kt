package com.example.ecoretosapp.ui.screens

/**
 * Pantalla principal del estudiante.
 * Muestra las opciones y secciones disponibles para que el usuario
 * pueda interactuar con los retos ecológicos.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun HomeEstudianteScreen(
    idUsuario: Int,
    viewModel: RetoViewModel = viewModel(),
    navController: NavController
) {
    val retos by viewModel.retos.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val retosAceptados by viewModel.retosAceptados.collectAsState()
    val puntosCompletados by viewModel.puntosCompletados.collectAsState()
    val retosCompletados by viewModel.retosCompletados.collectAsState()

    val retosPendientes = retos.filter { !retosAceptados.contains(it.idReto) }

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
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                HeaderRetos()
            }

            item {
                ImpactCard(
                    totalRetos = retosCompletados.size,
                    puntos = puntosCompletados
                )
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

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Retos activos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    Text(
                        text = "Ver todos",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF047857)
                    )
                }
            }

            if (retosPendientes.isEmpty()) {
                item {
                    Text(
                        text = "Ya aceptaste todos los retos disponibles 👏",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF047857)
                    )
                }
            }

            items(retosPendientes) { reto ->
                RetoCard(
                    titulo = reto.titulo,
                    descripcion = reto.descripcion,
                    puntos = reto.puntos,
                    dificultad = reto.dificultad,
                    onAceptar = {
                        viewModel.aceptarReto(
                            idReto = reto.idReto,
                            idUsuario = idUsuario
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun HeaderRetos() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Retos ecológicos",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
        }
    }
}

@Composable
private fun ImpactCard(
    totalRetos: Int,
    puntos: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF10B981), Color(0xFF84CC16))
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Tu impacto esta semana",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Text(
                            text = puntos.toString(),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Text(
                            text = "puntos verdes completados",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color.White.copy(alpha = 0.20f), RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🌿", fontSize = 42.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MiniStat("Completados", totalRetos.toString(), Modifier.weight(1f))
                    MiniStat("Racha", "7", Modifier.weight(1f))
                    MiniStat("Ranking", "#3", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun MiniStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.20f), RoundedCornerShape(18.dp))
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.9f)
        )
    }
}

@Composable
private fun RetoCard(
    titulo: String,
    descripcion: String,
    puntos: Int,
    dificultad: String,
    onAceptar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFFD1FAE5), RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "♻️", fontSize = 30.sp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = titulo,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Text(
                                text = "Reto • $dificultad",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B)
                            )
                        }

                        Text(
                            text = "+$puntos",
                            modifier = Modifier
                                .background(Color(0xFFDCFCE7), RoundedCornerShape(50.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF15803D)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = descripcion,
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

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
                    .clickable { onAceptar() },
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

