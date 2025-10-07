package com.example.app_gestionlotefarmaceutico.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// DataStore global
val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthDataStore(private val context: Context) {

    companion object {
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_ROLE = stringPreferencesKey("role")
        val KEY_NAME = stringPreferencesKey("nombre")
        val KEY_TOKEN = stringPreferencesKey("token")
    }

    // ✅ Guarda la información del usuario (correo, rol y nombre)
    suspend fun saveUser(email: String, role: String, nombre: String = "") {
        context.dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
            prefs[KEY_ROLE] = role
            prefs[KEY_NAME] = nombre
        }
    }

    // ✅ Guarda un token (si se requiere en producción)
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    // ✅ Obtiene el token actual
    suspend fun getToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_TOKEN]
    }

    // ✅ Obtiene el rol actual del usuario
    suspend fun getUserRole(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_ROLE]
    }

    // ✅ Obtiene el nombre (compatible con la versión demo)
    suspend fun getNombre(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_NAME]
    }

    // ✅ Alias opcional por compatibilidad
    suspend fun getRol(): String? = getUserRole()

    // ✅ Limpia todo (Logout)
    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
