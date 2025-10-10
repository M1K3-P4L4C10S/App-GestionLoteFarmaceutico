package com.example.app_gestionlotefarmaceutico.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_gestionlotefarmaceutico.ui.login.LoginScreen
import com.example.app_gestionlotefarmaceutico.ui.home.HomeScreen
import com.example.app_gestionlotefarmaceutico.ui.scan.QrScannerScreen
import com.example.app_gestionlotefarmaceutico.ui.admin.UsersScreen
import com.example.app_gestionlotefarmaceutico.ui.operator.MovimientosScreen
import com.example.app_gestionlotefarmaceutico.ui.consulta.ConsultaInventarioScreen
import com.example.app_gestionlotefarmaceutico.ui.scan.GenerateQrScreen

/**
 * 📍 Rutas principales de la aplicación (Versión DEMO)
 */
object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val SCAN = "scan"
    const val MOVS = "movimientos"
    const val USERS = "usuarios"
    const val CONSULTA = "consulta"
    const val GENERATE_QR = "generate_qr"
}

/**
 * 🚀 Controlador de navegación principal
 * Maneja la transición entre pantallas dentro de la app.
 */
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        // 🔐 Pantalla de Login
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 🏠 Pantalla principal
        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        // 📷 Escáner QR
        composable(Routes.SCAN) {
            QrScannerScreen(onQrDetected = {})
        }

        composable(Routes.MOVS) {
            MovimientosScreen(navController = navController)
        }


        // 👤 Gestión de usuarios (solo admin en demo)
        composable(Routes.USERS) {
            UsersScreen(navController)
        }

        // 📋 Consulta de inventario
        composable(Routes.CONSULTA) {
            ConsultaInventarioScreen()
        }

        // 🧾 Generador de códigos QR
        composable(Routes.GENERATE_QR) {
            GenerateQrScreen(navController)
        }
    }
}
