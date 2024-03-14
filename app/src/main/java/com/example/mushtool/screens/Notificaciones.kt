package com.example.mushtool.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun NotificacionesScreen(navController: NavHostController) {
    var areNotificationsEnabled by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        SettingItem(
            icon = androidx.compose.material.icons.Icons.Default.Notifications,
            title = "Notificaciones",
            switchState = areNotificationsEnabled,
            onSwitchToggled = { areNotificationsEnabled = it }
        )
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    switchState: Boolean,
    onSwitchToggled: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Switch(
            checked = switchState,
            onCheckedChange = onSwitchToggled
        )
    }
}

@Preview
@Composable
fun NotificacionesScreenPreview() {
    val navController = rememberNavController()
    NotificacionesScreen(navController)
}