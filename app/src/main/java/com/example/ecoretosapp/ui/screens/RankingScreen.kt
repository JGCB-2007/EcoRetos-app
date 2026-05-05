package com.example.ecoretosapp.ui.screens

/**
 * Pantalla encargada de mostrar el ranking de usuarios.
 * Permite visualizar la posición o puntaje de los participantes
 * según su avance en los retos ecológicos.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class UsuarioRanking(
    val nombre: String,
    val puntos: Int
)

@Composable
fun RankingScreen() {

    val usuarios = listOf(
        UsuarioRanking("Juan Pérez", 120),
        UsuarioRanking("María López", 100),
        UsuarioRanking("Carlos Ruiz", 90),
        UsuarioRanking("Ana Torres", 80),
        UsuarioRanking("Luis Gómez", 70)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF10B981), Color(0xFF84CC16))
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {

            // 🔹 Título
            Text(
                text = "Leaderboard",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 TOP 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                TopUserCircle("2", usuarios[1])
                TopUserCircle("1", usuarios[0], isMain = true)
                TopUserCircle("3", usuarios[2])
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Lista blanca abajo
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    usuarios.drop(3).forEachIndexed { index, user ->
                        RankingItem(index + 4, user)
                    }
                }
            }
        }
    }
}

@Composable
fun TopUserCircle(
    puesto: String,
    user: UsuarioRanking,
    isMain: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(if (isMain) 90.dp else 70.dp)
                    .background(Color.White, CircleShape)
            )

            Text(
                text = puesto,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = user.nombre,
            fontSize = 12.sp,
            color = Color.White
        )

        Text(
            text = "${user.puntos} pts",
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun RankingItem(
    posicion: Int,
    user: UsuarioRanking
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F5F9), RoundedCornerShape(18.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFD1FAE5), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("#$posicion", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(user.nombre, fontWeight = FontWeight.Bold)
            Text("Usuario ecológico", fontSize = 12.sp, color = Color.Gray)
        }

        Text(
            text = "${user.puntos} pts",
            color = Color(0xFF059669),
            fontWeight = FontWeight.Bold
        )
    }
}