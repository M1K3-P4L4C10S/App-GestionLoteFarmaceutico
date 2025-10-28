package com.example.app_gestionlotefarmaceutico.ui.operator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovimientosScreen(navController: NavHostController) {
    var codigo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("1") }
    var tipo by remember { mutableStateOf("entrada") }
    var msg by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar movimiento") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { tipo = "entrada" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == "entrada") MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("Entrada") }

                Button(
                    onClick = { tipo = "salida" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == "salida") MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("Salida") }
            }

            Button(
                onClick = { msg = "Movimiento registrado ($tipo: $cantidad unidades)" },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar") }

            msg?.let { Text(it) }
        }
    }
}
