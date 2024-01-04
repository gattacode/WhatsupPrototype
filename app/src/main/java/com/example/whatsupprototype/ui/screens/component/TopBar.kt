package com.example.whatsupprototype.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whatsupprototype.R
import com.example.whatsupprototype.ui.theme.Typography


@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipselogo),
            contentDescription = "ellipse",
            modifier = Modifier
                .size(width = 38.dp, height = 29.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "What's Up",
            style = Typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}