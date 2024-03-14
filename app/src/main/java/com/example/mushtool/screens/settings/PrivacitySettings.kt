
package com.example.mushtool.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mushtool.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward

@Composable
fun AccountSettingsScreen() {
    // Estados para los switches
    val profileVisibleState = remember { mutableStateOf(false) }
    val friendListState = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.backgroundapp4),
                contentDescription = "Fondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.6f
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp)
            ) {
                SectionTitle(title = "Visibilidad de la cuenta")
                SettingItem(
                    title = "Perfil Visible",
                    description = "Haz tu perfil visible para todos los usuarios",
                    hasSwitch = true,
                    switchState = profileVisibleState
                )

                Spacer(modifier = Modifier.height(30.dp))

                SectionTitle(title = "Compartir datos")
                SettingItem(
                    title = "Lista de amigos",
                    description = "Permite que otros vean tu lista de amigos",
                    hasSwitch = true,
                    switchState = friendListState
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle(title = "Mis amigos")
                SettingItem(
                    title = "Todo el mundo",
                    description = "Haz que tus publicaciones sean visibles para todos"
                )
                SettingItem(
                    title = "Mis amigos",
                    description = "Haz que tus publicaciones sean visibles solo para tus amigos"
                )
                SettingItem(
                    title = "Mis amigos",
                    description = "Haz que tus publicaciones sean visibles solo para tus amigos cercanos"
                )
                SettingItem(
                    title = "Mis amigos",
                    description = "Haz que tus publicaciones sean visibles solo para tus mejores amigos"
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(top = 25.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingItem(
    title: String,
    description: String? = null,
    hasSwitch: Boolean = true,
    switchState: MutableState<Boolean>? = null,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        if (hasSwitch) {
            Switch(
                checked = switchState?.value ?: false,
                onCheckedChange = { switchState?.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Preview
@Composable
fun AccountSettingsScreenPreview() {
    AccountSettingsScreen()
}