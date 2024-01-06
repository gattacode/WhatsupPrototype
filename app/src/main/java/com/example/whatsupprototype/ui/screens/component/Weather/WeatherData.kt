package com.example.whatsupprototype.ui.screens.component.Weather

import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.JsonParser

suspend fun getWeatherData(lat: Double, lon: Double, apiKey: String, language: String, unit: String): WeatherInfo {
    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&lang=$language&units=$unit"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val json = response.body?.string() ?: return@withContext WeatherInfo()
                val jsonObject = JsonParser.parseString(json).asJsonObject

                val weatherDescription = jsonObject.getAsJsonArray("weather").first().asJsonObject.get("description").asString
                val temperature = jsonObject.getAsJsonObject("main").get("temp").asDouble
                val cityName = jsonObject.get("name").asString
                val icon = jsonObject.getAsJsonArray("weather").first().asJsonObject.get("icon").asString

                WeatherInfo(cityName, temperature, weatherDescription, icon)
            } else {
                WeatherInfo()
            }
        } catch (e: Exception) {
            WeatherInfo()
        }
    }
}

data class WeatherInfo(
    val cityName: String = "Unavailable",
    val temperature: Double = 0.0,
    val weatherDescription: String = "Unavailable",
    val icon: String = "02d"
)
