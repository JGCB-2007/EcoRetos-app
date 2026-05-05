package com.example.ecoretosapp.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.ecoretosapp.ui.screens.HomeAdminScreen
import com.example.ecoretosapp.ui.screens.LoginScreen
import com.example.ecoretosapp.viewmodel.LoginViewModel
import com.example.ecoretosapp.ui.screens.MainEstudianteScreen
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    var idUsuarioLogueado by remember { mutableStateOf<Int?>(null) }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { usuario ->
                    idUsuarioLogueado = usuario.idUsuario

                    if (usuario.rol == "ESTUDIANTE") {
                        navController.navigate("home_estudiante") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else if (usuario.rol == "ADMINISTRADOR") {
                        navController.navigate("home_admin") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("home_estudiante") {
            MainEstudianteScreen(
                idUsuario = idUsuarioLogueado ?: 0,
                navController = navController,
                onLogout = {
                    idUsuarioLogueado = null
                    loginViewModel.resetLoginState()

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("home_admin") {
            HomeAdminScreen(
                onLogout = {
                    idUsuarioLogueado = null
                    loginViewModel.resetLoginState()

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}