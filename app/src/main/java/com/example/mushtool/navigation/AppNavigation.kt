package com.example.mushtool.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.screens.HomeScreen
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mushtool.screens.ProfileScreen
import com.example.mushtool.screens.settings.SettingScreen
import com.google.firebase.auth.FirebaseAuth

enum class Setas(){
    SetaNav
}
@Preview
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.background(Color(android.graphics.Color.rgb(210, 181, 163))) // Aplica el color aquí
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem: NavItems ->
                    NavigationBarItem(
                        modifier = Modifier,
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.shadow( 6.dp),
                                imageVector = navItem.icon,
                                contentDescription = null,
                                tint = Color(android.graphics.Color.rgb(210, 181, 163))
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                fontSize = 12.sp,
                                color = Color(android.graphics.Color.rgb(210, 181, 163))
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            // Aquí se añaden las pantallas
            composable(Screens.Home.name) {
                HomeScreen( navController = navController)
            }

            composable(Screens.Profile.name) {
                ProfileScreen()

            }

            composable(Screens.Settings.name) {
                SettingScreen(
                 signOut = { FirebaseAuth.getInstance().signOut() },
                navController = navController,
                context = navController.context
                )
        }
            }
        }
    }

