package com.example.app_gestionlotefarmaceutico.data.api

import com.example.app_gestionlotefarmaceutico.data.auth.AuthDataStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    /**
     * Crea el cliente Retrofit para consumir tu API REST.
     * Por ahora (versión demo) no agrega token, pero la estructura está lista.
     */
    suspend fun create(store: AuthDataStore? = null): Retrofit {
        // 🔹 Interceptor para ver las peticiones HTTP en Logcat (útil en desarrollo)
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // 🔹 Interceptor de autorización (inactivo por ahora)
        val authInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            // Si más adelante usas token JWT, descomenta:
            /*
            val token = store?.getToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            */

            chain.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()

        // Base URL temporal (usa la misma que definiste en BuildConfig)
        val baseUrl = "http://10.0.2.2:8080/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
