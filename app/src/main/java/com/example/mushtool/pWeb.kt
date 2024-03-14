package com.example.mushtool

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
import com.example.mushtool.Restaurante.ListaRestauranteFApp
import com.example.mushtool.Restaurante.MisRestaurantesFItems
import com.example.mushtool.ui.theme.MushToolTheme

sealed class ScreenRestauranteF(val ruta: String) {
    object LISTARESTAURANTE : ScreenRestauranteF("ListaRestauranteFApp")
    object DETALLESRESTAURANTE : ScreenRestauranteF("MisRestaurantesFItems")
}

class pWeb : ComponentActivity() {
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
                        startDestination = ScreenRestauranteF.LISTARESTAURANTE.ruta
                    ) {
                        composable(ScreenRestauranteF.LISTARESTAURANTE.ruta) {
                            ListaRestauranteFApp(navController = navController)
                        }
                        composable(ScreenRestauranteF.DETALLESRESTAURANTE.ruta) {

                            MisRestaurantesFItems(navController = navController)
                        }

                    }
                }
            }
        }
    }
}


