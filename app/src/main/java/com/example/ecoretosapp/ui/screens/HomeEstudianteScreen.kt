package com.example.ecoretosapp.ui.screens

/**
 * Pantalla principal del estudiante.
 * Muestra las opciones y secciones disponibles para que el usuario
 * pueda interactuar con los retos ecológicos.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.ecoretosapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecoretosapp.ui.components.BotonRecargar
import com.example.ecoretosapp.ui.components.IconoReto
import com.example.ecoretosapp.viewmodel.ImpactoUsuarioViewModel
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun HomeEstudianteScreen(
    idUsuario: Int,
    viewModel: RetoViewModel = viewModel(),
    impactoViewModel: ImpactoUsuarioViewModel = viewModel(),
    navController: NavController
) {
    val retos by viewModel.retos.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val retosAceptados by viewModel.retosAceptados.collectAsState()
    val impacto by impactoViewModel.impacto.collectAsState()
    val errorImpacto by impactoViewModel.error.collectAsState()

    val retosPendientes = retos.filter { reto ->
        !retosAceptados.contains(reto.idReto)
    }

    /*
     * Recarga automática cada vez que el usuario
     * entra a esta pantalla.
     */
    LaunchedEffect(idUsuario) {
        viewModel.cargarRetos()
        viewModel.cargarParticipaciones(idUsuario)
        impactoViewModel.cargarImpacto(idUsuario)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFECFDF5),
                        Color.White,
                        Color(0xFFF7FEE7)
                    )
                )
            )
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                HeaderRetos(
                    onRecargar = {
                        viewModel.cargarRetos()
                        viewModel.cargarParticipaciones(idUsuario)
                        impactoViewModel.cargarImpacto(idUsuario)
                    }
                )
            }

            item {
                ImpactCard(
                    totalRetos = impacto?.retosCompletados?.toInt() ?: 0,
                    puntos = impacto?.puntosTotales ?: 0,
                    racha = impacto?.rachaDias ?: 0,
                    ranking = impacto?.posicionRanking ?: 0
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

            if (errorImpacto != null) {
                item {
                    Text(
                        text = errorImpacto ?: "",
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
private fun HeaderRetos(
    onRecargar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Retos ecológicos",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF0F172A)
        )

        BotonRecargar(
            onRecargar = onRecargar
        )
    }
}

@Composable
private fun ImpactCard(
    totalRetos: Int,
    puntos: Int,
    racha: Int,
    ranking: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF10B981),
                            Color(0xFF84CC16)
                        )
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
                        modifier = Modifier.size(72.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.logo_ecoretos
                            ),
                            contentDescription = "Logo de EcoRetos",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MiniStat(
                        label = "Completados",
                        value = totalRetos.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    MiniStat(
                        label = "Racha",
                        value = racha.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    MiniStat(
                        label = "Ranking",
                        value = if (ranking > 0) "#$ranking" else "-",
                        modifier = Modifier.weight(1f)
                    )
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
            .background(
                Color.White.copy(alpha = 0.20f),
                RoundedCornerShape(18.dp)
            )
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
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconoReto(size = 56.dp)
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
                                .background(
                                    Color(0xFFDCFCE7),
                                    RoundedCornerShape(50.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                ),
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
                    .clickable {
                        onAceptar()
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