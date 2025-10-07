package com.example.app_gestionlotefarmaceutico.data.api

import com.example.app_gestionlotefarmaceutico.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/login") suspend fun login(@Body body: LoginRequest): Response<LoginResponse>
    @GET("api/users/me") suspend fun me(): Response<UserProfile>
    @GET("api/lotes/{qr}") suspend fun getLote(@Path("qr") qr: String): Response<LoteDto>
    @POST("api/movimientos") suspend fun registrarMovimiento(@Body body: MovimientoRequest): Response<Unit>
}
