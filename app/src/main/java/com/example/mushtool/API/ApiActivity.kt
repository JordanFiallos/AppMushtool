package com.example.mushtool.API

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.mushtool.API.Screens.Navigation
import com.example.mushtool.ui.theme.MushToolTheme

class ApiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(
                        listOf(
                            Color.LightGray,
                            Color.White,//MaterialTheme.colorScheme.primary,
                            Color.LightGray,//MaterialTheme.colorScheme.secondary,
                        )
                    )),
                    color = Color.Transparent
                ) {

                    Navigation()
                }
            }
        }
    }
}
