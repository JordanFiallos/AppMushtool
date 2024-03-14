package com.example.mushtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mushtool.ui.theme.MushToolTheme

class LGridSelect : ComponentActivity() {

    private val viewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                DesplegableScreen(viewModel)
            }
        }
    }

}

