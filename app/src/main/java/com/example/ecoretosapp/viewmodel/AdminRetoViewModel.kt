package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.CrearRetoRequest
import com.example.ecoretosapp.data.model.EditarRetoRequest
import com.example.ecoretosapp.data.model.Reto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminRetoViewModel : ViewModel() {

    private val _retos = MutableStateFlow<List<Reto>>(emptyList())
    val retos: StateFlow<List<Reto>> = _retos

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarRetos() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRetosAdmin()

                if (response.isSuccessful) {
                    _retos.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "No se pudieron cargar los retos"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun crearReto(
        titulo: String,
        descripcion: String,
        puntos: Int,
        dificultad: String,
        tipoValidacion: String,
        duracionHoras: Int,
        creadoPor: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.crearRetoAdmin(
                    CrearRetoRequest(
                        titulo = titulo,
                        descripcion = descripcion,
                        puntos = puntos,
                        dificultad = dificultad,
                        tipoValidacion = tipoValidacion,
                        creadoPor = creadoPor,
                        duracionHoras = duracionHoras
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Reto creado correctamente"
                    _error.value = null
                    cargarRetos()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo crear el reto"
                }
            } catch (e: Exception) {
                _error.value = "Error al crear reto: ${e.message}"
            }
        }
    }

    fun editarReto(
        idReto: Int,
        titulo: String,
        descripcion: String,
        puntos: Int,
        dificultad: String,
        tipoValidacion: String,
        duracionHoras: Int
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.editarRetoAdmin(
                    idReto = idReto,
                    request = EditarRetoRequest(
                        titulo = titulo,
                        descripcion = descripcion,
                        puntos = puntos,
                        dificultad = dificultad,
                        tipoValidacion = tipoValidacion,
                        duracionHoras = duracionHoras
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Reto actualizado correctamente"
                    _error.value = null
                    cargarRetos()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo actualizar el reto"
                }
            } catch (e: Exception) {
                _error.value = "Error al editar reto: ${e.message}"
            }
        }
    }

    fun eliminarReto(idReto: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.eliminarRetoAdmin(idReto)

                if (response.isSuccessful) {
                    _mensaje.value = "Reto desactivado correctamente"
                    _error.value = null
                    cargarRetos()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo desactivar el reto"
                }
            } catch (e: Exception) {
                _error.value = "Error al desactivar reto: ${e.message}"
            }
        }
    }
}