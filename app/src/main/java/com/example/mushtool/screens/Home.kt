package com.example.mushtool.screens


import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.API.ApiActivity
import com.example.mushtool.Forum.ForumActivity
import com.example.mushtool.Mymushrooms
import com.example.mushtool.R
import com.example.mushtool.navigation.AppNavigation
import com.example.mushtool.navigation.HomeScreens
import com.example.mushtool.newsMushtool.NewsActivity
import com.example.mushtool.plantillaBuscar
import com.example.mushtool.plantillaRecetas

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen( navController: NavHostController) {
    // Aquí va la implementación de la pantalla de inicio
    val image = painterResource(R.drawable.backgroundapp4)
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    }

    Box (modifier = Modifier.fillMaxSize()) {
        Image(
            painter = image,
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)

        ) {

            Text(
                text = "MushTool",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 40.sp,
            )

            Spacer(modifier = Modifier.size(20.dp))

            // Primera fila de IconButtons con texto debajo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(100.dp),
                        onClick = {
                            val intent = Intent(context, plantillaBuscar ::class.java)
                            context.startActivity(intent)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.iconobuscarseta),
                            contentDescription = "Buscar"
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp)) // Espacio entre la imagen y el texto
                    Text(
                        text = "Buscar",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.W400
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier.size(100.dp),
                        onClick = {
                            val intent = Intent(context, Mymushrooms ::class.java)
                            context.startActivity(intent)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.mapa),
                            contentDescription = "Mis setas"
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp)) // Espacio entre la imagen y el texto
                    Text(
                        text = "Mis setas",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.W400
                    )
                }
            }

            // Segunda fila de IconButtons con texto debajo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier.size(100.dp),
                        onClick = {
                            //plantillaRecetas ApiActivity
                            val intent = Intent(context, plantillaRecetas ::class.java)
                            context.startActivity(intent)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.recetas),
                            contentDescription = "Recetas"
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Espacio entre la imagen y el texto
                    Text(
                        text = "Recetas",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.W400
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier.size(100.dp),
                        onClick = {
                            val intent = Intent(context, GameActivity::class.java)
                            context.startActivity(intent)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.juegos),
                            contentDescription = "Aprender"
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Espacio entre la imagen y el texto
                    Text(
                        text = "Juega!",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.W400
                    )
                }
            }

            Spacer(modifier = Modifier.size(3.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }

                // Botones redondeados centrados
                RoundedButton(
                    "Noticias",
                    //NewsActivity
                    onClick = {  val intent = Intent(context, NewsActivity ::class.java)
                        context.startActivity(intent) },
                    painterResource(id = R.drawable.fondonat)

                )
                RoundedButton(
                    "Forum",
                    onClick = { val intent = Intent(context, ForumActivity::class.java)
                        context.startActivity(intent) },
                    painterResource(id = R.drawable.fondonat)
                )
                RoundedButton(
                    "Tiempo",
                    onClick = {
                         val intent = Intent(context, ApiActivity::class.java)
                        context.startActivity(intent) },
                    painterResource(id = R.drawable.fondonat)
                )
            }
        }
    }
}

private fun HomeScreens.Companion.Games(navController: NavHostController) {
    navController.navigate(route = "Games")

}

@Composable
fun RoundedButton(text: String, onClick: () -> Unit, backgroundColor: Painter) {
    Card(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(10.dp)
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(android.graphics.Color.rgb(210, 181, 163))
                ),
        ) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .shadow(8.dp, RoundedCornerShape(10.dp), clip = true)
                    .background(Color(android.graphics.Color.rgb(210, 181, 163)))
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
    AppNavigation()
}