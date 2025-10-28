package com.example.app_gestionlotefarmaceutico.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    var rol by remember { mutableStateOf("consulta") }
    var nombre by remember { mutableStateOf("Usuario") }

    LaunchedEffect(Unit) {
        rol = store.getUserRole() ?: "consulta"
        nombre = store.getNombre() ?: "Usuario"
    }

    val primaryBlue = Color(0xFF1565C0)
    val darkBlue = Color(0xFF0D47A1)
    val accentBlue = Color(0xFF2196F3)
    val lightAqua = Color(0xFFB2EBF2)
    val lightCeleste = Color(0xFFB3E5FC)
    val whiteAccent = Color(0xFFE8F5E9)

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(lightCeleste, whiteAccent, lightAqua)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gestión de Lotes Farmacéuticos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBlue)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = "Hola $nombre",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = darkBlue,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rol: ${rol.uppercase()}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = primaryBlue,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                val buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 5.dp)

                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue,
                    contentColor = Color.White
                )

                Button(onClick = { navController.navigate("scan") }, modifier = buttonModifier, colors = buttonColors, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Escanear QR", fontWeight = FontWeight.Bold)
                }

                Button(onClick = { navController.navigate("consulta") }, modifier = buttonModifier, colors = buttonColors, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Consulta de inventario", fontWeight = FontWeight.Bold)
                }

                Button(onClick = { navController.navigate("movimientos") }, modifier = buttonModifier, colors = buttonColors, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Registrar entrada/salida", fontWeight = FontWeight.Bold)
                }

                Button(onClick = { navController.navigate("admin") }, modifier = buttonModifier, colors = buttonColors, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.Group, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Administrar usuarios", fontWeight = FontWeight.Bold)
                }

                Button(onClick = { navController.navigate("generateqr") }, modifier = buttonModifier, colors = buttonColors, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.QrCode2, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Generar código QR", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedButton(
                    onClick = { navController.navigate("login") { popUpTo("home") { inclusive = true } } },
                    border = BorderStroke(2.dp, accentBlue),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
