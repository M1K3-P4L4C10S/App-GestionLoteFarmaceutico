package com.example.app_gestionlotefarmaceutico.data.model

data class LoteDto(
    val codigoQr: String,
    val nombre: String,
    val lote: String,
    val caducidad: String,
    val cantidad: Int,
    val notas: String? = null
)
