package com.example.whatsupprototype.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.whatsupprototype.ui.theme.SecondaryColor
import java.io.IOException
import java.util.Locale


fun getCityName(context: Context, lat: Double, long: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address> = geoCoder.getFromLocation(lat, long, 1) ?: return "localisation en cours..."
        addresses.firstOrNull()?.locality ?: addresses.firstOrNull()?.adminArea
        ?: addresses.firstOrNull()?.subAdminArea
        ?: "votre position" // L'api ne peut pas accéder à certaines régions (dont Lyon) dans ce cas la ville n'est pas trouvé malgré qu'on ait la position
    } catch (e: IOException) {
        "Unavailable"
    }
}