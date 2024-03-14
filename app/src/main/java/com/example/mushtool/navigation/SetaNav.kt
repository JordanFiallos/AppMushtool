package com.example.mushtool.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mushtool.screens.ListaSetasApp
import com.example.mushtool.screens.MisSetasItems


@Composable
fun SetaNav(navController: NavHostController){

    NavHost(navController = navController, startDestination = Screen.Lista.route){
        composable(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(500)
                )
            },
            route= Screen.Lista.route){
            ListaSetasApp(navController = navController)
        }
        composable(
            route= Screen.Detalle.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(500)
                )
            },
            arguments = listOf(
                navArgument("nombre"){
                    type = NavType.StringType
                }
            )
        ){
            val nombre = it.arguments!!.getString("nombre")
            //DetalleSeta(navController, nombre)

            MisSetasItems(navController, nombre = nombre )
        }
    }

}

const val MyMushroomsListPath  ="MyMushroomsList"
const val CardCustumPath ="CardCustum"
const val MisSetasItemsPath = "MisSetasItems"
sealed class Screen(val route:String){
    object  Lista:Screen("lista")
    object Detalle:Screen("detalle/{nombre}"){
        fun enviarSeta(nombre: String?)= "detalle/$nombre"
    }
    object Destails : Screen(MisSetasItemsPath)
}