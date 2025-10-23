package com.example.app_gestionlotefarmaceutico.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.app_gestionlotefarmaceutico.R
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import com.example.app_gestionlotefarmaceutico.ui.nav.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    var rol by remember { mutableStateOf("consulta") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        rol = store.getUserRole() ?: "consulta"
    }

    val azul = Color(0xFF1E88E5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Gestión de Lotes Farmacéuticos",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🏥 Logo centrado
                    Image(
                        painter = painterResource(id = R.drawable.logo_farmacia),
                        contentDescription = "Logo de la farmacia",
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Fit
                    )

                    // --- Botón Escanear QR ---
                    Button(
                        onClick = { nav.navigate(Routes.SCAN) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = azul)
                    ) {
                        Icon(Icons.Filled.QrCodeScanner, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Escanear QR")
                    }

                    // --- Botón Consulta ---
                    Button(
                        onClick = { nav.navigate(Routes.CONSULTA) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = azul)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Consulta de inventario")
                    }

                    // --- Botón Movimiento (si rol operador o admin) ---
                    if (rol.equals("operador", true) || rol.equals("admin", true)) {
                        Button(
                            onClick = { nav.navigate(Routes.MOVS) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = azul)
                        ) {
                            Icon(Icons.Filled.AddBox, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Registrar entrada/salida")
                        }
                    }

                    // --- Botón Usuarios (solo admin) ---
                    if (rol.equals("admin", true)) {
                        Button(
                            onClick = { nav.navigate(Routes.USERS) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = azul)
                        ) {
                            Icon(Icons.Filled.ManageAccounts, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Administrar usuarios")
                        }
                    }

                    // --- Botón Generar QR (admin o almacenista) ---
                    if (rol.equals("admin", true) || rol.equals("almacenista", true)) {
                        Button(
                            onClick = { nav.navigate(Routes.GENERATE_QR) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = azul)
                        ) {
                            Icon(Icons.Filled.QrCode2, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Generar código QR")
                        }
                    }

                    // --- Botón Cerrar sesión ---
                    OutlinedButton(
                        onClick = {
                            scope.launch { store.clear() }
                            nav.navigate(Routes.LOGIN) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Cerrar sesión")
                    }
                }
            }
        }
    }
}
