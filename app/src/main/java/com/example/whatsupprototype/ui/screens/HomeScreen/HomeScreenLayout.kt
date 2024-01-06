package com.example.whatsupprototype.ui.screens.HomeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.whatsupprototype.ui.screens.component.News.NewsSection
import com.example.whatsupprototype.ui.screens.component.Todo.Todo
import com.example.whatsupprototype.ui.screens.component.Weather.Weather
import com.example.whatsupprototype.ui.screens.component.TopBar
import com.example.whatsupprototype.ui.theme.Typography
import com.example.whatsupprototype.ui.theme.SecondaryColor

@Composable
fun HomeScreenLayout(userName: String, latitude: Double, longitude: Double) {
    Column {
        TopBar()
        GreetingMessage(userName)
        Weather(latitude, longitude)
        NewsSection()
        Todo()
    }
}

@Composable
fun GreetingMessage(userName: String) {
    Row(modifier = Modifier.padding(top = 39.dp, start = 24.dp)) {
        Text(
            text = "What's up ",
            style = Typography.titleLarge
        )
        Text(
            text = userName,
            color = SecondaryColor,
            style = Typography.titleLarge
        )
        Text(
            text = " ?",
            style = Typography.titleLarge
        )
    }
}