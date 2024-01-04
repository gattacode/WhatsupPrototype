package com.example.whatsupprototype.ui.screens.component

import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun Meteo(onCityNameReceived: (String) -> Unit) {
    val context = LocalContext.current

    // Ideally, this function should be called from a ViewModel or similar.
    // Directly calling it inside a composable is not recommended.
    fun getCityName(lat: Double, long: Double): String {
        var cityName: String = "votre ville"
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address.firstOrNull()?.adminArea
            ?: address.firstOrNull()?.locality
                    ?: address.firstOrNull()?.subAdminArea
                    ?: "Unknown"
        return cityName
    }

    val latitude = 40.7128 // Example latitude
    val longitude = -74.0060 // Example longitude

    val cityName = getCityName(latitude, longitude)
    onCityNameReceived(cityName)

}
