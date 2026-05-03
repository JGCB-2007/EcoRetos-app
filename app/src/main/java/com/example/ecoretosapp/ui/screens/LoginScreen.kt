package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
                    listOf(
                        Color(0xFFECFDF5),
                        Color.White,
                        Color(0xFFF7FEE7)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF10B981), Color(0xFF84CC16))
                        ),
                        RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🌿",
                    fontSize = 52.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Bienvenido a",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF047857)
            )

            Text(
                text = "EcoRetos UAM",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Iniciá sesión para completar retos ecológicos y ganar puntos verdes.",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "CIF",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = cif,
                        onValueChange = { cif = it },
                        placeholder = { Text("Ingresá tu CIF") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF10B981),
                            unfocusedBorderColor = Color(0xFFD1FAE5),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color(0xFFF8FAFC)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Contraseña",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Ingresá tu contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF10B981),
                            unfocusedBorderColor = Color(0xFFD1FAE5),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color(0xFFF8FAFC)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.login(cif, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF059669)
                        )
                    ) {
                        Text(
                            text = "Entrar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (state) {
                        is LoginUiState.Loading -> {
                            CircularProgressIndicator(
                                color = Color(0xFF059669)
                            )
                        }

                        is LoginUiState.Error -> {
                            Text(
                                text = (state as LoginUiState.Error).mensaje,
                                color = Color(0xFFDC2626),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
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
}