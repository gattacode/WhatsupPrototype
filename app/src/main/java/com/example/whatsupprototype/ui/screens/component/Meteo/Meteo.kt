@file:Suppress("DEPRECATION")

package com.example.whatsupprototype.ui.screens.component.Meteo

import android.content.Context
import android.location.Geocoder
import android.location.Address
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.whatsupprototype.ui.theme.SecondaryColor
import java.util.Locale
import java.io.IOException

fun getCityName(context: Context, lat: Double, long: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address> = geoCoder.getFromLocation(lat, long, 1) ?: return "Unknown"
        addresses.firstOrNull()?.locality ?: addresses.firstOrNull()?.adminArea
        ?: addresses.firstOrNull()?.subAdminArea ?: "Unknown"
    } catch (e: IOException) {
        "Unavailable"
    }
}

@Composable
fun Meteo() {
    Column(modifier = Modifier.padding(start = 24.dp)){
        Ville()
    }
}

@Composable
fun Ville() {
    val context = LocalContext.current
    var cityName by remember { mutableStateOf("Loading...") }

    val latitude = 40.7128
    val longitude = -74.0060

    LaunchedEffect(key1 = Unit) {
        cityName = getCityName(context, latitude, longitude)
    }

    Row {
        Text(
            text = "La météo aujourd’hui à ",
        );
        Text(
            text = cityName,
            color = SecondaryColor
        )
    }
}