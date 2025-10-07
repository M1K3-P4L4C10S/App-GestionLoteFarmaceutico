package com.example.app_gestionlotefarmaceutico.data.model

data class LoginRequest(val correo: String, val password: String)
data class LoginResponse(val token: String, val rol: String, val nombre: String)
data class UserProfile(val id: Int, val nombre: String, val correo: String, val rol: String)
