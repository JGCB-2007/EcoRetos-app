package com.example.ecoretosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ecoretosapp.navigation.AppNavigation
import com.example.ecoretosapp.ui.screens.LoginScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                    AppNavigation()
                }

        }
    }
