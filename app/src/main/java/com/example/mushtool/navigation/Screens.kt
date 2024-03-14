package com.example.mushtool.navigation

enum class Screens  {
    Home,
    Profile,
    Settings,
   // Search
}

enum class HomeScreens {
    Home,
    Games,
    Lista,
    Setas,
    PlantillaBuscar;

    companion object {
        fun fromRoute(route: String): HomeScreens = when (route) {
            "Home" -> Home
            "Games" -> Games
            "Lista" -> Lista
            "Setas" -> Setas
            "PlantillaBuscar" -> PlantillaBuscar
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }
}