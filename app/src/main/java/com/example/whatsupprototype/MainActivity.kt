package com.example.whatsupprototype


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatsupprototype.ui.screens.HomeScreen.HomeScreenLayout
import com.example.whatsupprototype.ui.screens.component.UsernameForm
import com.example.whatsupprototype.ui.screens.component.Utils.LocationDisplay
import com.example.whatsupprototype.ui.theme.WhatsupPrototypeTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitudeState = mutableStateOf<Double?>(null)
    private var longitudeState = mutableStateOf<Double?>(null)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                latitudeState.value = location.latitude
                longitudeState.value = location.longitude
            }
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationRequest?.let {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(it, locationCallback, null)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkAndRequestLocationPermission()

        startLocationUpdates()

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
                        HomeScreenLayout(username!!, latitudeState.value ?: 0.0, longitudeState.value ?: 0.0)
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
            fetchLastKnownLocation()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startLocationUpdates()
            } else {
                // Handle the case where permissions are denied
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
