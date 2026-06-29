package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.AdminDashboardResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminDashboardViewModel : ViewModel() {

    private val _dashboard = MutableStateFlow<AdminDashboardResponse?>(null)
    val dashboard: StateFlow<AdminDashboardResponse?> = _dashboard

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarDashboard() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getAdminDashboard()

                if (response.isSuccessful) {
                    _dashboard.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "No se pudo cargar el dashboard"
                }
            } catch (e: Exception) {
                _error.value = "Error dashboard: ${e.message}"
            }
        }
    }
}