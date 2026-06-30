package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecoretosapp.ui.components.ConfirmacionDialog
import com.example.ecoretosapp.viewmodel.PerfilViewModel
import com.example.ecoretosapp.viewmodel.RetoViewModel

@Composable
fun PerfilEstudianteScreen(
    idUsuario: Int,
    retoViewModel: RetoViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel(),
    perfilViewModel: PerfilViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit
) {
    val usuario by perfilViewModel.usuario.collectAsState()
    val error by perfilViewModel.error.collectAsState()
    val insignias by perfilViewModel.insignias.collectAsState()
    val impacto by perfilViewModel.impacto.collectAsState()
    val todasLasInsignias by
    perfilViewModel.todasLasInsignias.collectAsState()

    var confirmarLogout by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(idUsuario) {
        perfilViewModel.cargarUsuario(idUsuario)
        perfilViewModel.cargarInsignias(idUsuario)
        perfilViewModel.cargarImpacto(idUsuario)
        perfilViewModel.cargarTodasLasInsignias()
    }

    val nombre = usuario?.nombreCompleto ?: "Cargando..."
    val puntos = usuario?.puntosTotales ?: 0
    val cif = usuario?.cif ?: "Sin CIF"
    val correo = usuario?.correoInstitucional ?: "Sin correo"
    val nivel = obtenerNivelEco(puntos)

    val iniciales = nombre
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") {
            it.first().uppercase()
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
        Column(
            verticalArrangement =
                Arrangement.spacedBy(16.dp),
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
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
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
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(82.dp)
                                .background(
                                    Color.White.copy(
                                        alpha = 0.20f
                                    ),
                                    RoundedCornerShape(50.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = iniciales,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

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
                            color = Color.White.copy(
                                alpha = 0.90f
                            )
                        )

                        Text(
                            text = cif,
                            fontSize = 13.sp,
                            color = Color.White.copy(
                                alpha = 0.85f
                            )
                        )

                        Text(
                            text = correo,
                            fontSize = 13.sp,
                            color = Color.White.copy(
                                alpha = 0.85f
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {
                            PerfilStat(
                                label = "Retos",
                                value = (
                                        impacto?.retosCompletados ?: 0
                                        ).toString(),
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

            todasLasInsignias.forEach { insignia ->

                val obtenida = insignias.any {
                    it.idInsignia == insignia.idInsignia
                }

                MedalCard(
                    icono = insignia.iconoUrl ?: "🏅",
                    titulo = insignia.nombre,
                    descripcion = if (obtenida) {
                        insignia.descripcion
                    } else {
                        "Disponible desde " +
                                "${insignia.puntosMinimos} puntos"
                    },
                    desbloqueada = obtenida
                )
            }

            LogoutCard(
                onClick = {
                    confirmarLogout = true
                }
            )

            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }

        if (confirmarLogout) {
            ConfirmacionDialog(
                titulo = "Cerrar sesión",
                mensaje =
                    "¿Seguro que deseas cerrar sesión?",
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
            .clickable {
                onClick()
            },
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
                Text(
                    text = "🚪",
                    fontSize = 24.sp
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDC2626)
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
            .background(
                Color.White.copy(alpha = 0.20f),
                RoundedCornerShape(18.dp)
            )
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
    descripcion: String,
    desbloqueada: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (desbloqueada) {
                Color.White
            } else {
                Color(0xFFF1F5F9)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (desbloqueada) {
                3.dp
            } else {
                1.dp
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = if (desbloqueada) {
                            Color(0xFFD1FAE5)
                        } else {
                            Color(0xFFE2E8F0)
                        },
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (desbloqueada) {
                    Text(
                        text = icono,
                        fontSize = 28.sp
                    )
                } else {
                    MedallaBloqueadaIcon()
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (desbloqueada) {
                        Color(0xFF0F172A)
                    } else {
                        Color(0xFF64748B)
                    }
                )

                Text(
                    text = descripcion,
                    fontSize = 13.sp,
                    color = if (desbloqueada) {
                        Color(0xFF64748B)
                    } else {
                        Color(0xFF94A3B8)
                    }
                )
            }
        }
    }
}

@Composable
private fun MedallaBloqueadaIcon() {
    Canvas(
        modifier = Modifier.size(34.dp)
    ) {
        val ancho = size.width
        val alto = size.height
        val centroX = ancho / 2f

        val colorCinta = Color(0xFF9CA3AF)
        val colorMedalla = Color(0xFF6B7280)
        val colorCentro = Color(0xFFD1D5DB)

        val cintaIzquierda = Path().apply {
            moveTo(centroX - 10f, 0f)
            lineTo(centroX - 2f, alto * 0.48f)
            lineTo(centroX - 11f, alto * 0.56f)
            lineTo(centroX - 17f, 0f)
            close()
        }

        val cintaDerecha = Path().apply {
            moveTo(centroX + 10f, 0f)
            lineTo(centroX + 2f, alto * 0.48f)
            lineTo(centroX + 11f, alto * 0.56f)
            lineTo(centroX + 17f, 0f)
            close()
        }

        drawPath(
            path = cintaIzquierda,
            color = colorCinta
        )

        drawPath(
            path = cintaDerecha,
            color = colorCinta
        )

        drawCircle(
            color = colorMedalla,
            radius = ancho * 0.30f,
            center = androidx.compose.ui.geometry.Offset(
                x = centroX,
                y = alto * 0.66f
            )
        )

        drawCircle(
            color = colorCentro,
            radius = ancho * 0.15f,
            center = androidx.compose.ui.geometry.Offset(
                x = centroX,
                y = alto * 0.66f
            )
        )
    }
}

fun obtenerNivelEco(
    puntos: Int
): String {
    return when {
        puntos >= 500 -> "Maestro"
        puntos >= 300 -> "Guardián"
        puntos >= 150 -> "Protector"
        puntos >= 50 -> "Explorador"
        else -> "Novato"
    }
}