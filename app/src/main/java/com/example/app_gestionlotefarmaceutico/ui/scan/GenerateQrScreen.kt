package com.example.app_gestionlotefarmaceutico.ui.scan

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GenerateQrScreen(navController: NavHostController) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var lote by remember { mutableStateOf(TextFieldValue("")) }
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var caducidad by remember { mutableStateOf(TextFieldValue("")) }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var saveMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Generar Código QR de Producto", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lote,
            onValueChange = { lote = it },
            label = { Text("Código de lote") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = caducidad,
            onValueChange = { caducidad = it },
            label = { Text("Fecha de caducidad (AAAA-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val contenido = """
                    Producto: ${nombre.text}
                    Lote: ${lote.text}
                    Cantidad: ${cantidad.text}
                    Caducidad: ${caducidad.text}
                """.trimIndent()

                qrBitmap = generateQRCode(contenido)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.text.isNotBlank() && lote.text.isNotBlank()
        ) {
            Text("Generar QR")
        }

        qrBitmap?.let { bitmap ->
            Spacer(Modifier.height(16.dp))
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Código QR generado",
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val saved = saveQrImageWithDate(context, bitmap, lote.text)
                    saveMessage = if (saved) "✅ QR guardado correctamente" else "❌ Error al guardar"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar QR en galería")
            }

            saveMessage?.let {
                Text(
                    it,
                    color = if (it.contains("Error")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }

            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("Volver al inicio")
            }
        }
    }
}

/**
 * 🧩 Genera el código QR a partir de un texto
 */
fun generateQRCode(text: String): Bitmap? {
    val writer = QRCodeWriter()
    return try {
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}

/**
 * 💾 Guarda el QR con el nombre del lote y la fecha actual.
 */
fun saveQrImageWithDate(context: android.content.Context, bitmap: Bitmap, loteId: String): Boolean {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.getDefault())
    val fileName = "${loteId}_${dateFormat.format(Date())}.png"
    return try {
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QR_Codes")
        if (!directory.exists()) directory.mkdirs()
        val file = File(directory, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}
