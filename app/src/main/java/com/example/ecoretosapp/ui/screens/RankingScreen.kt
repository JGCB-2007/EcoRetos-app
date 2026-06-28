package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.ecoretosapp.data.model.RankingResponse
import com.example.ecoretosapp.viewmodel.RankingViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun RankingScreen(
    viewModel: RankingViewModel = viewModel()
) {
    val ranking by viewModel.ranking.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarRanking()
    }

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
            Text(
                text = "Ranking EcoRetos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (error != null) {
                Text(
                    text = error ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            val top3 = ranking.take(3)

            if (top3.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    if (top3.size >= 2) {
                        TopUserCircle("2", top3[1])
                    } else {
                        Spacer(modifier = Modifier.width(70.dp))
                    }

                    TopUserCircle("1", top3[0], isMain = true)

                    if (top3.size >= 3) {
                        TopUserCircle("3", top3[2])
                    } else {
                        Spacer(modifier = Modifier.width(70.dp))
                    }
                }
            } else {
                Text(
                    text = "Aún no hay estudiantes en el ranking.",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp)
                ) {
                    items(
                        ranking.drop(3)
                    ) { user ->
                        RankingItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun TopUserCircle(
    puesto: String,
    user: RankingResponse,
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
            text = user.nombreCompleto,
            fontSize = 12.sp,
            color = Color.White
        )

        Text(
            text = "${user.puntosTotales} pts",
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun RankingItem(
    user: RankingResponse
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
            Text("#${user.posicion}", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(user.nombreCompleto, fontWeight = FontWeight.Bold)
            Text("Usuario ecológico", fontSize = 12.sp, color = Color.Gray)
        }

        Text(
            text = "${user.puntosTotales} pts",
            color = Color(0xFF059669),
            fontWeight = FontWeight.Bold
        )
    }
}