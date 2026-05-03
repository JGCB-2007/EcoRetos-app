package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun PerfilEstudianteScreen(
    retoViewModel: RetoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val retosAceptados by retoViewModel.retosAceptados.collectAsState()

    val nombre = "Estudiante UAM"
    val puntos = 120

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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = "Mi cuenta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF047857)
                )

                Text(
                    text = "Perfil",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(6.dp)
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(82.dp)
                                .background(
                                    Color.White.copy(alpha = 0.20f),
                                    RoundedCornerShape(50.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("👤", fontSize = 42.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = nombre,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "$puntos puntos verdes",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White.copy(alpha = 0.90f)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            PerfilStat(
                                label = "Retos",
                                value = retosAceptados.size.toString(),
                                modifier = Modifier.weight(1f)
                            )

                            PerfilStat(
                                label = "Medallas",
                                value = "3",
                                modifier = Modifier.weight(1f)
                            )

                            PerfilStat(
                                label = "Nivel",
                                value = "Eco",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Text(
                text = "Medallas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            MedalCard(
                icono = "🌿",
                titulo = "Eco Novato",
                descripcion = "Primeros pasos ecológicos"
            )

            MedalCard(
                icono = "♻️",
                titulo = "Reciclador Activo",
                descripcion = "Participación en retos verdes"
            )

            MedalCard(
                icono = "🏆",
                titulo = "Guardián Verde",
                descripcion = "Buen avance acumulando puntos"
            )
        }
    }
}

@Composable
private fun PerfilStat(
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.90f)
        )
    }
}

@Composable
private fun MedalCard(
    icono: String,
    titulo: String,
    descripcion: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFFD1FAE5), RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(icono, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = descripcion,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}