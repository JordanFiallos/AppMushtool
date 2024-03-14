package com.example.mushtool.screens.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mushtool.R

@Composable
fun MemoryGame() {
    val images = listOf(
        R.drawable.gurumelo,
        R.drawable.agricus,
        R.drawable.amanitamuscaria,
        R.drawable.entoloma_nidorosum,
        R.drawable.boleto_de_satan_s,
        R.drawable.gurumelo,
        R.drawable.agricus,
        R.drawable.amanitamuscaria,
        R.drawable.entoloma_nidorosum,
        R.drawable.boleto_de_satan_s
    )

    var selectedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var index = 0
        while (index < images.size) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(5) {
                    if (index < images.size) {
                        MemoryCard(
                            painter = painterResource(id = images[index]),
                            isSelected = selectedIndices.contains(index),
                            onClick = {
                                selectedIndices = selectedIndices + index
                                if (selectedIndices.size == 2) {
                                    val firstIndex = selectedIndices[0]
                                    val secondIndex = selectedIndices[1]
                                    if (images[firstIndex] == images[secondIndex]) {
                                        selectedIndices = emptyList()
                                    } else {
                                        showDialog = true
                                    }
                                }
                            }
                        )
                    }
                    index++
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Incorrect match!") },
            text = { Text("Try again!") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedIndices = emptyList()
                        showDialog = false
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun MemoryCard(painter: Painter, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painter,
            contentDescription = "Memory Card",
            modifier = Modifier.fillMaxSize(),
            alpha = if (isSelected) 0.5f else 1.0f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryGamePreview() {
    MemoryGame()
}