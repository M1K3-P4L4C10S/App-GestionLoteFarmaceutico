package com.example.app_gestionlotefarmaceutico.data.model

data class MovimientoRequest(
    val codigoQr: String,
    val tipo: String, // "entrada" | "salida"
    val cantidad: Int
)
