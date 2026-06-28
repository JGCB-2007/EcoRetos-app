package com.example.ecoretosapp.ui.screens

/**
 * Pantalla encargada de mostrar la información del perfil del usuario.
 * Presenta datos personales o información relacionada con la cuenta
 * dentro de la aplicación.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.ecoretosapp.viewmodel.RetoViewModel
import com.example.ecoretosapp.viewmodel.PerfilViewModel
import com.example.ecoretosapp.ui.components.ConfirmacionDialog

@Composable
fun PerfilEstudianteScreen(
    idUsuario: Int,
    retoViewModel: RetoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    perfilViewModel: PerfilViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit
){
    val retosAceptados by retoViewModel.retosAceptados.collectAsState()
    val usuario by perfilViewModel.usuario.collectAsState()
    val error by perfilViewModel.error.collectAsState()
    val insignias by perfilViewModel.insignias.collectAsState()
    var confirmarLogout by remember { mutableStateOf(false) }

    LaunchedEffect(idUsuario) {
        perfilViewModel.cargarUsuario(idUsuario)
        perfilViewModel.cargarInsignias(idUsuario)
    }
    val nombre = usuario?.nombreCompleto ?: "Cargando..."
    val puntos = usuario?.puntosTotales ?: 0
    val cif = usuario?.cif ?: "Sin CIF"
    val correo = usuario?.correoInstitucional ?: "Sin correo"
    val nivel = obtenerNivelEco(puntos)
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                if (error != null) {
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
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
                        Text(
                            text = cif,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )

                        Text(
                            text = correo,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
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
                                value = insignias.size.toString(),
                                modifier = Modifier.weight(1f)
                            )

                            PerfilStat(
                                label = "Nivel",
                                value = nivel,
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

            if (insignias.isEmpty()) {
                Text(
                    text = "Aún no has obtenido insignias.",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )
            } else {
                insignias.forEach { insignia ->
                    MedalCard(
                        icono = insignia.iconoUrl ?: "🏅",
                        titulo = insignia.nombre,
                        descripcion = insignia.descripcion
                    )
                }
            }

            LogoutCard(
                onClick = {
                    confirmarLogout = true
                }
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
        if (confirmarLogout) {
            ConfirmacionDialog(
                titulo = "Cerrar sesión",
                mensaje = "¿Seguro que deseas cerrar sesión?",
                textoConfirmar = "Cerrar sesión",
                textoCancelar = "Cancelar",
                onConfirmar = {
                    confirmarLogout = false
                    onLogout()
                },
                onCancelar = {
                    confirmarLogout = false
                }
            )
        }
    }
}

@Composable
fun LogoutCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Color(0xFFD1FAE5),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🚪", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Text(
                    text = "Volver a la pantalla de inicio",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )

            }

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

fun obtenerNivelEco(puntos: Int): String {
    return when {
        puntos >= 500 -> "Maestro"
        puntos >= 300 -> "Guardián"
        puntos >= 150 -> "Protector"
        puntos >= 50 -> "Explorador"
        else -> "Novato"
    }
}

