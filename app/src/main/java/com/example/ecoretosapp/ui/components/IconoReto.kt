package com.example.ecoretosapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ecoretosapp.R

@Composable
fun IconoReto(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    Image(
        painter = painterResource(
            id = R.drawable.icono_reto
        ),
        contentDescription = "Ícono de reto ecológico",
        modifier = modifier.size(size),
        contentScale = ContentScale.Fit
    )
}