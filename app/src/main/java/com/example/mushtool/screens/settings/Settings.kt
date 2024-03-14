package com.example.mushtool.screens.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.R
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.mushtool.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

private val languages = listOf("Español", "Inglés", "Catalán", "Alemán")

@Composable
fun SettingScreen(
    signOut: (Any?) -> Unit,
    navController: NavHostController,
    context: android.content.Context,
) {
    var selectedLanguage by remember { mutableStateOf("Español") }
    var isDarkThemeEnabled by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f
        )
        Box(
          modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp, bottom = 50.dp, top = 50.dp)
                    .alpha(0.6f)
                    .background(Color.White, shape = RoundedCornerShape(40.dp))
                ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            ) {
                Text(
                    text = "Ajustes",
                    fontSize = 50.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp),
                    textAlign = TextAlign.Center
                )

                SettingItem(
                    icon = Icons.Default.NotificationsActive,
                    title = "Notificaciones",
                    onClick = {
                        val intent = Intent(context, NotificationActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                SettingItem(
                    icon = Icons.Default.Policy,
                    title = "Privacidad",
                    onClick = {
                        val intent = Intent(context, PrivacityActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                SettingItem(
                    icon = Icons.Default.QuestionMark,
                    title = "Ayuda y Soporte",
                    onClick = {
                        val intent = Intent(context, HelpAndSupportActivity::class.java)
                        context.startActivity(intent)
                    }
                )

                // Separador
                Divider(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))

                // Idioma
                Text(
                    text = "Seleccionar Idioma:",
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Lista desplegable para seleccionar idioma
                LanguageDropdown(
                    selectedLanguage = selectedLanguage,
                    onLanguageSelected = { language ->
                        selectedLanguage = language
                    }
                )
                // Botón para cerrar sesión en Firebase
                Button(
                    onClick = { signOut(context) }, // Método para cerrar sesión
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        }
    }
}
@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(languages) { language ->
            LanguageItem(
                language = language,
                isSelected = language == selectedLanguage,
                onClick = { onLanguageSelected(language) }
            )
        }
    }
}

@Composable
fun LanguageItem(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = language,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}