package com.example.app_gestionlotefarmaceutico.ui.operator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import kotlinx.coroutines.launch

@Composable
fun MovimientosScreen() {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    var codigo by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("entrada") }
    var cantidad by remember { mutableStateOf("1") }
    var msg by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar movimiento (DEMO)", style = MaterialTheme.typography.titleLarge)
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = tipo == "entrada",
                onClick = { tipo = "entrada" },
                label = { Text("Entrada") }
            )
            FilterChip(
                selected = tipo == "salida",
                onClick = { tipo = "salida" },
                label = { Text("Salida") }
            )
        }

        Button(
            onClick = {
                scope.launch {
                    // Simulación local sin backend
                    msg = if (codigo.isNotEmpty()) {
                        "✅ Movimiento registrado correctamente ($tipo de $cantidad unidades)"
                    } else {
                        "⚠️ Ingresa un código válido"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar movimiento")
        }

        if (msg != null) {
            Text(msg!!, color = MaterialTheme.colorScheme.primary)
        }
    }
}
