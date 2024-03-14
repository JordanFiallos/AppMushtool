package com.example.mushtool.screens

import android.content.Intent
import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.R

@Composable
fun GamesScreen(navController: NavController) {
    val games = listOf("Adivina la Seta", "Tipos de Setas")
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.backgroundapp4),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aprende jugando!",
                color = Color.Black,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Dale para jugar",
                color = Color.Black,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            games.forEach { game ->
                GameButton(
                    text = game,
                    onClick = {
                        if (game == "Tipos de Setas") {
                            navigateToMushroomGame(navController)
                        } else {
                            val intent = Intent(context, GuessGameActivity::class.java)
                            context.startActivity(intent)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

fun navigateToMushroomGame(navController: NavController) {
    val intent = Intent(navController.context, MushroomTypeActivity::class.java)
    navController.context.startActivity(intent)
}

@Composable
fun GameButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), clip = true, ),
        colors = ButtonDefaults.buttonColors( Color(rgb(210, 181, 163)))
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun GamesScreenPreview() {
    // Para la vista previa, simplemente pasamos un NavController vacío
    val navController = rememberNavController()
    GamesScreen(navController = navController)

}