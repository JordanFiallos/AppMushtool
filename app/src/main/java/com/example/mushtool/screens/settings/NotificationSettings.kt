package com.example.mushtool.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mushtool.R

@Composable
fun NotificationSettings() {
    val allNotificationsState = remember { mutableStateOf(true) }
    val soundState = remember { mutableStateOf(true) }
    val vibrationState = remember { mutableStateOf(true) }
    val silencioState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f
        )

        Text (
            text = "Configuración de notificaciones",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 100.dp , bottom = 500.dp)
        )
        Column(
            modifier = Modifier
                .padding(50.dp)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(0.5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sonido",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = soundState.value && allNotificationsState.value,
                    onCheckedChange = { soundState.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6200EE),
                        checkedTrackColor = Color(0x1A6200EE),
                        uncheckedThumbColor = Color(0xFF6200EE),
                        uncheckedTrackColor = Color(0x1A000000)
                    )
                )
            }

            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Reproducir un sonido cuando se reciba una notificación",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Todas las notificaciones",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = allNotificationsState.value,
                    onCheckedChange = {
                        allNotificationsState.value = it
                        soundState.value = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6200EE),
                        checkedTrackColor = Color(0x1A6200EE),
                        uncheckedThumbColor = Color(0xFF6200EE),
                        uncheckedTrackColor = Color(0x1A000000)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vibración",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = vibrationState.value,
                    onCheckedChange = { vibrationState.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6200EE),
                        checkedTrackColor = Color(0x1A6200EE),
                        uncheckedThumbColor = Color(0xFF6200EE),
                        uncheckedTrackColor = Color(0x1A000000)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Programar silencio",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = silencioState.value,
                    onCheckedChange = { silencioState.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6200EE),
                        checkedTrackColor = Color(0x1A6200EE),
                        uncheckedThumbColor = Color(0xFF6200EE),
                        uncheckedTrackColor = Color(0x1A000000)
                    )
                )
            }

            Button(
                onClick = {
                    allNotificationsState.value = true
                    soundState.value = true
                    vibrationState.value = true
                    silencioState.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(45.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(4.dp, RoundedCornerShape(16.dp), clip = true),
                colors = ButtonDefaults.buttonColors(
                    Color(
                        android.graphics.Color.rgb(
                            210,
                            181,
                            163
                        )
                    )
                )

            ) {
                Text(
                    text = "Restablecer configuración",
                    style = MaterialTheme.typography.headlineSmall ,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview
@Composable
fun NotificationSettingsPreview() {
    NotificationSettings()
}