package com.example.mushtool.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
data class NavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItems(
        "Menú",
        Icons.Filled.Menu,
        route = "home"
    ),
    NavItems(
        "Perfil",
        Icons.Filled.ManageAccounts,
        "profile"
    ),
    NavItems(
        "Ajustes",
        Icons.Filled.Settings,
        "settings"
    )
)