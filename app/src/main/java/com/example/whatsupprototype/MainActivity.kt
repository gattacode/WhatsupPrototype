package com.example.whatsupprototype

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whatsupprototype.ui.screens.HomeScreen.HomeScreenLayout
import com.example.whatsupprototype.ui.screens.UsernameForm
import com.example.whatsupprototype.ui.theme.WhatsupPrototypeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the saved username if it exists
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", null)

        setContent {
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
                            username = chosenUsername // Update the state
                        }
                    } else {
                        HomeScreenLayout(username!!)
                    }
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
