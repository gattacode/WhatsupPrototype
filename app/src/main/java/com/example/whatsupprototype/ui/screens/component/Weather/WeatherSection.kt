package com.example.whatsupprototype.ui.screens.component.Weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.whatsupprototype.R
import com.example.whatsupprototype.ui.theme.SecondaryColor
import com.example.whatsupprototype.ui.theme.Typography
import com.example.whatsupprototype.utils.getCityName

@Composable
fun Weather(latitude: Double, longitude: Double) {
    Column(modifier = Modifier.padding(start = 24.dp)) {
        WeatherTitle(latitude, longitude)
        WeatherForecast(latitude, longitude, "05ed99a4f4092af9d594bb01a3f73784", "fr", "metric")
    }
}

@Composable
fun WeatherTitle(latitude: Double, longitude: Double) {
    val context = LocalContext.current
    var cityName by remember { mutableStateOf("Loading...") }

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

@Composable
fun WeatherForecast(
    latitude: Double,
    longitude: Double,
    apiKey: String,
    language: String,
    unit: String
) {
    var weatherInfo by remember { mutableStateOf(WeatherInfo()) }

    LaunchedEffect(key1 = Unit) {
        weatherInfo = getWeatherData(latitude, longitude, apiKey, language, unit)
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = "http://openweathermap.org/img/wn/${weatherInfo.icon}@4x.png",
            contentDescription = "Weather Icon",
            modifier = Modifier
                .height(150.dp)
                .weight(1f),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ellipselogo)
        )
        Column(modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "${weatherInfo.temperature}°C",
                style = Typography.titleLarge
            )
            Text(
                text = weatherInfo.weatherDescription,
                color = SecondaryColor
            )
        }
    }
}