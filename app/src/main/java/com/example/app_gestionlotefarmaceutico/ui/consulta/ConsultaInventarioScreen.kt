package com.example.app_gestionlotefarmaceutico.ui.consulta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class LoteItem(
    val nombre: String,
    val lote: String,
    val caducidad: String,
    val cantidad: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaInventarioScreen(navController: NavHostController) {
    val inventario = listOf(
        LoteItem("Paracetamol 500mg", "L-202310", "2026-03-15", 120),
        LoteItem("Ibuprofeno 400mg", "L-202401", "2025-12-10", 75),
        LoteItem("Amoxicilina 250mg", "L-202308", "2025-07-01", 60),
        LoteItem("Omeprazol 20mg", "L-202405", "2027-01-05", 200),
        LoteItem("Suero Oral", "L-202309", "2025-08-30", 45)
    )

    // 🔍 Filtramos los productos próximos a caducar (menos de 6 meses)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val hoy = LocalDate.now()
    val proximosAVencer = inventario.filter {
        val cad = LocalDate.parse(it.caducidad, formatter)
        ChronoUnit.MONTHS.between(hoy, cad) <= 6
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Inventario de Medicamentos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.MOVS) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF71A8F7))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        "Inventario general",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                items(inventario) { item ->
                    InventarioCard(item)
                }

                if (proximosAVencer.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Medicamentos por vencer",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(proximosAVencer) { item ->
                        InventarioCard(item, isExpiringSoon = true)
                    }
                }
            }
        }
    }
}

@Composable
fun InventarioCard(item: LoteItem, isExpiringSoon: Boolean = false) {
    val barColor = if (isExpiringSoon) Color(0xFFFF9800) else Color(0xFF71A8F7)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    item.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                if (isExpiringSoon) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD32F2F), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Próximo a vencer",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text("Lote: ${item.lote}", style = MaterialTheme.typography.bodySmall)
            Text("Caducidad: ${item.caducidad}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = item.cantidad / 200f,
                color = barColor,
                trackColor = Color(0xFFE0E0E0),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Cantidad disponible: ${item.cantidad}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
