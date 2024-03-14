package com.example.mushtool.screens.games

import android.content.Intent
import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.R
import com.example.mushtool.games.MushroomGuessGameLvL2Activity
import com.example.mushtool.screens.PlayActivity

@Composable
fun GuessGameScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1)), // Fondo color crema
        color = MaterialTheme.colorScheme.background
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
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Adivina qué es lo que ves en la foto",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(R.drawable.logojuego),
                    contentDescription = "Ejemplo de imagen",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.padding(30.dp))

            Button(
                onClick = {
                    val intent = Intent(context, PlayActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp), clip = true),
                colors = ButtonDefaults.buttonColors(Color(rgb(229, 211, 195)))
            ) {
                Text(
                    text = "Nivel 1",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = {
                    val intent = Intent(context, MushroomGuessGameLvL2Activity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp), clip = true),
                colors = ButtonDefaults.buttonColors(Color(rgb(229, 211, 195)))
            ) {
                Text(
                    text = "Nivel 2",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuessGameScreenPreview() {
    GuessGameScreen()
}