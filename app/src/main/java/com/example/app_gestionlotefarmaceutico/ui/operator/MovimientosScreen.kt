package com.example.app_gestionlotefarmaceutico.ui.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovimientosScreen(navController: NavController) {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    var codigo by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("entrada") }
    var cantidad by remember { mutableStateOf("1") }
    var msg by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Consultar Lotes",
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
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF71A8F7))
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Registrar movimiento (DEMO)",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código QR / Lote") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )


                Button(
                    onClick = {
                        scope.launch {
                            msg = if (codigo.isNotEmpty()) {
                                "✅ Movimiento registrado correctamente ($tipo de $cantidad unidades)"
                            } else {
                                "⚠️ Ingresa un código válido"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF71A8F7))
                ) {
                    Text("Guardar movimiento", color = Color.White)
                }

                if (msg != null) {
                    Text(msg!!, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        var tipo by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { tipo = "entrada" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tipo == "entrada") Color(0xFF4CAF50) else Color(0xFFBDBDBD)
                )
            ) {
                Text("Registrar Entrada", color = Color.White)
            }

            Spacer(Modifier.width(16.dp))

            Button(
                onClick = { tipo = "salida" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tipo == "salida") Color(0xFFE57373) else Color(0xFFBDBDBD)
                )
            ) {
                Text("Registrar Salida", color = Color.White)
            }
        }

    }
}
