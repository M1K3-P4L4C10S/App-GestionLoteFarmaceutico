package com.example.app_gestionlotefarmaceutico.ui.consulta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class LoteItem(val nombre: String, val lote: String, val caducidad: String, val cantidad: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaInventarioScreen(navController: NavHostController) {
    val inventario = listOf(
        LoteItem("Paracetamol 500mg", "L-202310", "2026-03-15", 120),
        LoteItem("Ibuprofeno 400mg", "L-202401", "2025-12-10", 75)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario de Medicamentos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(inventario) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(item.nombre, fontSize = 18.sp)
                        Text("Lote: ${item.lote}", fontSize = 14.sp, color = Color.Gray)
                        Text("Caducidad: ${item.caducidad}", fontSize = 14.sp, color = Color.Gray)
                        Text("Cantidad: ${item.cantidad}", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
