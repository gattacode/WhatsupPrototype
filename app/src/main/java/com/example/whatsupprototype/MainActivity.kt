package com.example.whatsupprototype

import android.content.Context
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whatsupprototype.ui.screens.HomeScreen.HomeScreenLayout
import com.example.whatsupprototype.ui.screens.UsernameForm
import com.example.whatsupprototype.ui.theme.WhatsupPrototypeTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatsupprototype.ui.screens.component.LocationDisplay
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitudeState = mutableStateOf<Double?>(null)
    private var longitudeState = mutableStateOf<Double?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRequestLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // Retrieve the saved username if it exists
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", null)

        setContent {
            latitudeState = remember { mutableStateOf<Double?>(null) }
            longitudeState = remember { mutableStateOf<Double?>(null) }
            WhatsupPrototypeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Manage the username state
                    var username by remember { mutableStateOf(savedUsername) }

                    if (username == null) {
                        UsernameForm { chosenUsername ->
                            saveUsername(this@MainActivity, chosenUsername)
                            username = chosenUsername
                        }
                    } else {
                        HomeScreenLayout(username!!)
                        LocationDisplay(latitudeState.value, longitudeState.value)
                    }
                }
            }
        }
    }
    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Les permissions sont déjà accordées
        }
    }
    private fun fetchLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    latitudeState.value = it.latitude
                    longitudeState.value = it.longitude
                }
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    fetchLastKnownLocation()
                } else {
                    // Gérer le cas où les permissions sont refusées
                }
            }
        }
    }
}

fun saveUsername(context: Context, username: String) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putString("username", username)
        apply()
    }
}
