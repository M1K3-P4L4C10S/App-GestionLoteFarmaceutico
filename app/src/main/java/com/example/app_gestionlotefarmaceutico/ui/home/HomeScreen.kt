package com.example.app_gestionlotefarmaceutico.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    var rol by remember { mutableStateOf("consulta") }
    var nombre by remember { mutableStateOf("Usuario") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        rol = store.getUserRole() ?: "consulta"
        nombre = store.getNombre() ?: "Usuario"
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Lotes Farmacéuticos") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Hola, $nombre", style = MaterialTheme.typography.titleLarge)
            Text("Rol: $rol")

            Button(onClick = { nav.navigate(Routes.SCAN) }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.QrCodeScanner, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Escanear QR")
            }

            Button(onClick = { nav.navigate(Routes.CONSULTA) }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Consulta de inventario")
            }

            if (rol.equals("operador", true) || rol.equals("admin", true)) {
                Button(onClick = { nav.navigate(Routes.MOVS) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.AddBox, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Registrar entrada/salida")
                }
            }

            if (rol.equals("admin", true)) {
                Button(onClick = { nav.navigate(Routes.USERS) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.ManageAccounts, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Administrar usuarios")
                }
            }

            if (rol.equals("admin", true) || rol.equals("almacenista", true)) {
                Button(onClick = { nav.navigate(Routes.GENERATE_QR) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.QrCode2, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Generar código QR")
                }
            }

            Spacer(Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    scope.launch { store.clear() }
                    nav.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión")
            }
        }
    }
}
