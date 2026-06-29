package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecoretosapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onFinish()
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .shadow(12.dp, RoundedCornerShape(36.dp))
                    .background(Color.White, RoundedCornerShape(36.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_ecoretos),
                    contentDescription = "Logo EcoRetos",
                    modifier = Modifier.size(105.dp)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "EcoRetos",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF047857)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Pequeñas acciones, grandes cambios",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(34.dp))

            CircularProgressIndicator(
                color = Color(0xFF10B981),
                strokeWidth = 3.dp,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}