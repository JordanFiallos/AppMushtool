package com.example.mushtool.Forum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.ui.theme.MushToolTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

val database: DatabaseReference = Firebase.database.reference

class ForumActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ChatScreens()
                    //ChatScreenss(viewModel)

                    val navController = rememberNavController()

                    // Proporciona una instancia de ChatViewModel utilizando viewModel()
                    val chatViewModel: ChatViewModel = viewModel()

                    val onImageClick: (String) -> Unit = {}

                    NavHost(
                        navController = navController,
                        startDestination = "chatScreen"
                    ) {
                        composable("chatScreen") {
                            // Llama a la función ChatScreen
                            ChatScreen(chatViewModel = chatViewModel, navController = navController, onItemClick = chatViewModel::selectMessage, onImageClick = onImageClick)
                        }

                        // Puedes agregar otras composables según tus necesidades
                    }
                }
            }
        }
    }

}
