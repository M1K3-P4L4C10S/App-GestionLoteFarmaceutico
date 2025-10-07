package com.example.app_gestionlotefarmaceutico.data.auth

class AuthRepository(private val store: AuthDataStore) {

    suspend fun login(email: String, password: String): Result<String> {
        return when {
            email.equals("admin@sgfqr.com", ignoreCase = true) && password == "1234" -> {
                store.saveUser("admin@sgfqr.com", "ADMIN")
                Result.success("ADMIN")
            }
            email.equals("almacen@sgfqr.com", ignoreCase = true) && password == "1234" -> {
                store.saveUser("almacen@sgfqr.com", "ALMACENISTA")
                Result.success("ALMACENISTA")
            }
            email.equals("supervisor@sgfqr.com", ignoreCase = true) && password == "1234" -> {
                store.saveUser("supervisor@sgfqr.com", "SUPERVISOR")
                Result.success("SUPERVISOR")
            }
            else -> Result.failure(Exception("Usuario o contraseña incorrectos"))
        }
    }
}
