package com.example.app_gestionlotefarmaceutico.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gestionlotefarmaceutico.R
import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import com.example.app_gestionlotefarmaceutico.data.auth.AuthRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val ctx = LocalContext.current
    val store = remember { AuthDataStore(ctx) }
    val repo = remember { AuthRepository(store) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Header Azul con Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    color = Color(0xFF7DB6F6),
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.logo_farmacia),
                    contentDescription = "Logo",
                    modifier = Modifier.size(160.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Iniciar Sesión",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))

        // 🧍 Usuario
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Usuario",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(50.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // 🔑 Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_key),
                    contentDescription = "Contraseña",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(50.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "¿Olvidó su contraseña? Contacte al administrador.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 🔵 Botón “Acceder”
        Button(
            onClick = {
                scope.launch {
                    loading = true
                    val res = repo.login(email.trim(), password.trim())
                    loading = false
                    if (res.isSuccess) onLoginSuccess() else error = "Credenciales incorrectas"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF497BC9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .height(55.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                if (loading) "Accediendo..." else "Acceder",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Este es un sistema cerrado. Para registrarse, contacte a su administrador.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp),
            lineHeight = 16.sp
        )
    }
}
