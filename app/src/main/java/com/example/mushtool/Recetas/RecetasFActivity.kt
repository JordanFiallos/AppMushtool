package com.example.mushtool.Recetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.ui.theme.MushToolTheme

sealed class ScreenF(val ruta: String) {
    object LISTARECETAS : ScreenF("ListaRecetasFApp")
    object DETALLESRECETASF : ScreenF("MisRecetasItems")
}

class RecetasFActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenF.LISTARECETAS.ruta
                    ) {
                        composable(ScreenF.LISTARECETAS.ruta) {
                            ListaRecetasFApp(navController = navController)
                        }
                        composable(ScreenF.DETALLESRECETASF.ruta) {

                            MisRecetasItems(navController = navController)
                        }

                    }
                }
            }
        }
    }
}
