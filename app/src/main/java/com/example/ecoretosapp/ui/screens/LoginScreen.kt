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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.data.model.LoginResponse
import com.example.ecoretosapp.viewmodel.LoginUiState
import com.example.ecoretosapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (LoginResponse) -> Unit
) {

    var cif by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.loginState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "EcoRetos",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C5364)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Inicia sesión",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = cif,
                    onValueChange = { cif = it },
                    label = { Text("CIF") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.login(cif, password)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Ingresar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (state) {
                    is LoginUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is LoginUiState.Error -> {
                        Text(
                            text = (state as LoginUiState.Error).mensaje,
                            color = Color.Red
                        )
                    }

                    is LoginUiState.Success -> {
                        val user = (state as LoginUiState.Success).usuario

                        LaunchedEffect(Unit) {
                            onLoginSuccess(user)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}