package com.example.whatsupprototype.ui.screens.component.Weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.whatsupprototype.utils.Ville

@Composable
fun Meteo(latitude: Double, longitude: Double) {
    Column(modifier = Modifier.padding(start = 24.dp)) {
        Ville(latitude, longitude)
        WeatherForecast(latitude, longitude, "05ed99a4f4092af9d594bb01a3f73784", "fr","metric" )
    }
}

@Composable
fun WeatherForecast(latitude: Double, longitude: Double, apiKey: String, language: String, unit : String) {
    var weatherInfo by remember { mutableStateOf(WeatherInfo()) }

    LaunchedEffect(key1 = Unit) {
        weatherInfo = getWeatherData(latitude, longitude, apiKey, language, unit)
    }

    Column {
        Text(text = "Ville: ${weatherInfo.cityName}")
        Text(text = "Température: ${weatherInfo.temperature}°C") // Assuming the temperature is in Celsius
        Text(text = "Météo: ${weatherInfo.weatherDescription}")
    }
}

