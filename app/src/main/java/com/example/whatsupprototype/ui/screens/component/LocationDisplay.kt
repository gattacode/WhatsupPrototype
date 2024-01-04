package com.example.whatsupprototype.ui.screens.component
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LocationDisplay(latitude: Double?, longitude: Double?) {
    if (latitude != null && longitude != null) {
        Text(text = "Latitude: $latitude, Longitude: $longitude")
    } else {
        Text(text = "Localisation non disponible")
    }
}
