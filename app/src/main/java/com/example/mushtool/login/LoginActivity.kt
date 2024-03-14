package com.example.mushtool.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.R
import com.example.mushtool.screens.HomeActivity
import com.example.mushtool.ui.theme.MushToolTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                val navController = rememberNavController()
                val context = this

                NavHost(navController = navController, startDestination = "Login") {
                    composable("Login") {
                        LoginScreen(
                            navController = navController,
                            context = applicationContext,
                            onLogin = {
                                navigateToHomeActivity(context)
                            }
                        )
                    }
                    composable("Register") {
                        RegisterScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    context: Context,
    onLogin: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colorScheme.background) {
        Image(
            painter = painterResource(id = R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Inicio de sesión",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            var passwordVisibility by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                        )
                    }
                }
            )
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        var errorMessage = " Por favor, completar los campos."
                    } else {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            email,
                            password
                        ).addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                onLogin()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                Text(text = "Iniciar sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavHostController) {
    val auth = Firebase.auth

    var email by remember { mutableStateOf("") }
    var fNacimiento by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colorScheme.background) {
        Image(
            painter = painterResource(id = R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = fNacimiento,
                onValueChange = { fNacimiento = it },
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    if (username.isNotEmpty() && fNacimiento.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    val userId = user?.uid ?: ""
                                    val userData = hashMapOf(
                                        "username" to username,
                                        "fNacimiento" to fNacimiento,
                                        "email" to email,
                                    )
                                    val database = Firebase.firestore
                                    database.collection("usuarios")
                                        .document(userId)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            navController.navigate("login")
                                        }
                                } else {
                                    var errorMessage = "Error al registrar el usuario."
                                }
                            }
                    } else {
                        var errorMessage = "Por favor, completar los campos."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Registrarse")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .align(Alignment.End)
                    .background(color = Color(android.graphics.Color.rgb(211, 194, 187)))

            ) {
                Text(text = "¿Ya tienes cuenta? Inicia sesión aquí")
            }
        }
    }
}

private fun navigateToHomeActivity(
    context: Context,
) {
    val intent = Intent(context, HomeActivity::class.java)
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    MushToolTheme {
        RegisterScreen(navController = rememberNavController())
    }
}