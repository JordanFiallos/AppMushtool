package com.example.mushtool.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.R

@Preview
@Composable
fun ProfileScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var newEmail by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordConfirmation by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo de la pantalla
        Image(
            painter = painterResource(id = R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp, bottom = 200.dp, top = 150.dp)
                .alpha(0.5f)
                .background(Color.White, shape = RoundedCornerShape(40.dp)) // Recorta el contenido del box según la forma redondeada
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), // Ajuste del padding para desplazar hacia arriba
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la pantalla
                Text(
                    text = "Perfil",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Foto de perfil y nombre del usuario
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfilicono),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Mairén Pérez",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "mairen.perez@example.com",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "********", // Se muestra la contraseña como asteriscos por seguridad
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Botón para editar el perfil
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar perfil",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                showDialog = true
                            }
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Opción para configurar las preferencias
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Preferencias",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Botón para configurar preferencias
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configurar preferencias",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                // Acción de configurar preferencias
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, bottom = 200.dp, top = 150.dp)
                    .alpha(0.9f)
                    .background(Color.Gray, shape = RoundedCornerShape(40.dp)),
                title = { Text(text = "Editar Perfil") },
                confirmButton = {
                    Button(
                        onClick = {
                            // Realizar la lógica de actualización del perfil aquí
                            showDialog = false
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Cancelar")
                    }
                },
                text = {
                    Column {
                        Text("Editar correo electrónico:")
                        textInputField(
                            value = newEmail,
                            onValueChange = {
                                val it = newEmail
                                newEmail = it
                            },
                            label = "Correo electrónico"
                        )
                        Text("Editar contraseña:")
                        textInputField(
                            value = newPassword,
                            onValueChange = {
                                val it = newPassword
                                newPassword = it
                            },
                            label = "Contraseña"
                        )
                        Text("Confirmar nueva contraseña:")
                        textInputField(
                            value = newPasswordConfirmation,
                            onValueChange = {
                                val it = newPasswordConfirmation
                                newPasswordConfirmation = it
                            },
                            label = "Confirmar contraseña"
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun textInputField(value: String, onValueChange: () -> Unit, label: String) {
    var text by remember { mutableStateOf(value) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange()
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
