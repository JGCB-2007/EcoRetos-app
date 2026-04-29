package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.Reto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RetoViewModel : ViewModel() {

    private val _retos = MutableStateFlow<List<Reto>>(emptyList())
    val retos: StateFlow<List<Reto>> = _retos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cargarRetos() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRetos()
                if (response.isSuccessful) {
                    _retos.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error HTTP: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun aceptarReto(idReto: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.aceptarReto(
                    idReto = idReto,
                    request = AceptarRetoRequest(idUsuario = idUsuario)
                )

                if (response.isSuccessful) {
                    _mensaje.value = response.body()?.mensaje ?: "Reto aceptado correctamente"
                    _error.value = null
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo aceptar el reto"
                }

            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }
}