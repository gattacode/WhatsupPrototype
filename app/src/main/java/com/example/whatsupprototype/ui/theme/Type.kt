package com.example.whatsupprototype.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.whatsupprototype.R

val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.Medium,
        color = Color.Black,
    ),
    bodyMedium = TextStyle(
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.Medium,
        color = Color.Black,
    ),
    bodyLarge = TextStyle(
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black,
    ),
    bodySmall = TextStyle(
        fontSize = 10.sp,
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.Normal,
        color = Color(0xFF000000),
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.montserrat)),
        fontWeight = FontWeight.Normal,
        color = Color(0xFF1C1B1F),
    )
    // Add other text styles as needed
)
