package com.example.whatsupprototype.ui.screens.component

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsupprototype.ui.theme.SecondaryColor

@Composable
fun UsernameForm(onUsernameChosen: (String) -> Unit) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            label = { Text("Comment vous appelez-vous ?") }
        )
        Button(
            onClick = { onUsernameChosen(username) }, colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryColor,
                contentColor = Color.White
            )
        ) {
            Text("Envoyer")
        }
    }
}
