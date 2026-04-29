package com.example.ecoretosapp.data.api

import com.example.ecoretosapp.data.model.LoginRequest
import com.example.ecoretosapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.example.ecoretosapp.data.model.Reto
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.ParticipacionResponse
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