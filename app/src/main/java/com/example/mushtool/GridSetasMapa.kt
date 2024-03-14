package com.example.mushtool

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mushtool.Model.Seta

@Composable
fun DesplegableScreen(desplegableViewModel: DataViewModel) {
    val opciones by desplegableViewModel.setaFirestore.collectAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var seleccionada: Seta? by remember { mutableStateOf(null) }

    Column {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Seleccionar Seta")
        }

        // Menú desplegable
        if (expanded) {
            LazyColumn {
                items(opciones) { seta ->
                    Text(
                        text = seta.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                seleccionada = seta
                                expanded = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }

        // Texto que muestra la opción seleccionada
        Text(
            text = "Seta Seleccionada: ${seleccionada?.nombre.orEmpty()}",
            modifier = Modifier.padding(16.dp)
        )

    }
}

