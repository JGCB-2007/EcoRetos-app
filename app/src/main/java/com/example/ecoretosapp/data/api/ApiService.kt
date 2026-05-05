package com.example.ecoretosapp.data.api

/**
 * Define las operaciones disponibles para comunicarse con la API del sistema EcoRetos.
 * Aquí se especifican los endpoints (GET, POST, etc.) como login, obtención de retos
 * y registro de participación de usuarios.
 */

import com.example.ecoretosapp.data.model.LoginRequest
import com.example.ecoretosapp.data.model.LoginResponse
import com.example.ecoretosapp.data.model.Reto
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.ParticipacionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("retos")
    suspend fun getRetos(): Response<List<Reto>>

    @POST("retos/{idReto}/aceptar")
    suspend fun aceptarReto(
        @Path("idReto") idReto: Int,
        @Body request: AceptarRetoRequest
    ): Response<ParticipacionResponse>
}