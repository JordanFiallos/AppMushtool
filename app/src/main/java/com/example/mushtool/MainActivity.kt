package com.example.mushtool
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.login.LoginActivity
import com.example.mushtool.navigation.AppNavigation
import com.example.mushtool.screens.HomeScreen
import com.example.mushtool.ui.theme.MushToolTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        FirebaseApp.initializeApp(this)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            navigateToLogin()
            return
        }

        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen(navController = rememberNavController())
                    AppNavigation()
                }
            }
        }

        fun signInWithEmailAndPassword(email: String, password: String) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.d(TAG, "Conexión Exitosa")
                    Toast.makeText(this, "Conexión Exitosa.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Conexión Fallida", exception)
                    Toast.makeText(this, "Conexión Fallida.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MushToolTheme {
        HomeScreen(navController = rememberNavController())
        AppNavigation()
    }
}

