package com.example.app_gestionlotefarmaceutico.ui.consulta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class LoteItem(
    val nombre: String,
    val lote: String,
    val caducidad: String,
    val cantidad: Int
)

@Composable
fun ConsultaInventarioScreen() {
    val inventario = listOf(
        LoteItem("Paracetamol 500mg", "L-202310", "2026-03-15", 120),
        LoteItem("Ibuprofeno 400mg", "L-202401", "2025-12-10", 75),
        LoteItem("Amoxicilina 250mg", "L-202308", "2025-07-01", 60),
        LoteItem("Omeprazol 20mg", "L-202405", "2027-01-05", 200),
        LoteItem("Suero Oral", "L-202309", "2025-08-30", 45)
    )

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Consulta de Inventario", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(inventario) { item ->
                Card(
                    Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Producto: ${item.nombre}", style = MaterialTheme.typography.titleMedium)
                        Text("Lote: ${item.lote}")
                        Text("Caducidad: ${item.caducidad}")
                        Text("Cantidad: ${item.cantidad}")
                    }
                }
            }
        }
    }
}
