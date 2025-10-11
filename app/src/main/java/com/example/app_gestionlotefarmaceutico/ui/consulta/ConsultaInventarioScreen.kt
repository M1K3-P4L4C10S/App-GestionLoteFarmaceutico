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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes



data class LoteItem(
    val nombre: String,
    val lote: String,
    val caducidad: String,
    val cantidad: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaInventarioScreen(navController: NavController) {

    val inventario = listOf(
        LoteItem("Paracetamol 500mg", "L-202310", "2026-03-15", 120),
        LoteItem("Ibuprofeno 400mg", "L-202401", "2025-12-10", 75),
        LoteItem("Amoxicilina 250mg", "L-202308", "2025-07-01", 60),
        LoteItem("Omeprazol 20mg", "L-202405", "2027-01-05", 200),
        LoteItem("Suero Oral", "L-202309", "2025-08-30", 45)
    )

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
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF71A8F7)
                )
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFEAF2FF), Color(0xFFFFFFFF))
                    )
                )
                .padding(16.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(inventario) { item ->
                    val porcentaje = (item.cantidad / 200f).coerceIn(0f, 1f)
                    val porcentajeTexto = "${(porcentaje * 100).toInt()}%"

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(20.dp))
                            .padding(horizontal = 2.dp)
                    ) {
                        Column(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.nombre,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color(0xFF3C4A73),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = porcentajeTexto,
                                    color = Color(0xFF4A6CF7),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(Color(0xFFF0F2FA), RoundedCornerShape(50))
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxWidth(porcentaje)
                                        .height(10.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    Color(0xFF4A6CF7),
                                                    Color(0xFFB8C0FF)
                                                )
                                            ),
                                            shape = RoundedCornerShape(50)
                                        )
                                )
                            }

                            Spacer(Modifier.height(10.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Lote: ${item.lote}",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Caducidad: ${item.caducidad}",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
