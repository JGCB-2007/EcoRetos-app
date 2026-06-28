package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.RankingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RankingViewModel : ViewModel() {

    private val _ranking = MutableStateFlow<List<RankingResponse>>(emptyList())
    val ranking: StateFlow<List<RankingResponse>> = _ranking

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarRanking() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRanking()

                if (response.isSuccessful) {
                    _ranking.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "No se pudo cargar el ranking"
                }

            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }
}