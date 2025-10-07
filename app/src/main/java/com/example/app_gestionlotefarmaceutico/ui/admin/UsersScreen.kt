package com.example.app_gestionlotefarmaceutico.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes

@OptIn(ExperimentalMaterial3Api::class) // 👈 Esta línea es la clave
@Composable
fun UsersScreen(nav: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Usuarios") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Pantalla de administración de usuarios",
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = { nav.navigate(Routes.HOME) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
