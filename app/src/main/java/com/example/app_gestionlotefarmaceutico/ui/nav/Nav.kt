package com.example.app_gestionlotefarmaceutico.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_gestionlotefarmaceutico.ui.login.LoginScreen
import com.example.app_gestionlotefarmaceutico.ui.home.HomeScreen
import com.example.app_gestionlotefarmaceutico.ui.consulta.ConsultaInventarioScreen
import com.example.app_gestionlotefarmaceutico.ui.admin.UsersScreen
import com.example.app_gestionlotefarmaceutico.ui.operator.MovimientosScreen
import com.example.app_gestionlotefarmaceutico.ui.generateqr.GenerateQRScreen
import com.example.app_gestionlotefarmaceutico.ui.scan.QrScannerScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(onLoginSuccess = { navController.navigate("home") })
        }

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("consulta") {
            ConsultaInventarioScreen(navController = navController)
        }

        composable("admin") {
            UsersScreen(navController = navController)
        }

        composable("movimientos") {
            MovimientosScreen(navController = navController)
        }

        composable("generateqr") {
            GenerateQRScreen(navController = navController)
        }

        composable("scan") {
            QrScannerScreen(navController = navController)
        }
    }
}
