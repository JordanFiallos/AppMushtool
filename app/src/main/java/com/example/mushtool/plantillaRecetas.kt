package com.example.mushtool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.Glosari.myGlossarryListMusht
import com.example.mushtool.Recetas.RecetasFActivity
import com.example.mushtool.ui.theme.MushToolTheme

class plantillaRecetas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Rec_RestaurantApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MushToolTheme {
        Rec_RestaurantApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Rec_RestaurantApp() {

    val goMain by remember { mutableIntStateOf(0) }
    val goRecipe by remember { mutableIntStateOf(1) }
    val goRestaurant by remember { mutableIntStateOf(2) }
    val goGlossary by remember { mutableIntStateOf(4) }
    val goRestaurant2 by remember { mutableIntStateOf(3) }
    val context = LocalContext.current

    val  url = "https://www.thefork.es/restaurantes/terrassa-c547168"

    Scaffold(
        topBar = {
            // ... (previous code)
        }
    ) { containt ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(containt)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    contentDescription = null,
                    alpha = 0.7f,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text (
                    text = "Tus Recetas",
                    fontSize = 40.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 160.dp, bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button_Receta(goRecipe) {
                            val intent = Intent(context, RecetasFActivity::class.java)
                            context.startActivity(intent)
                        }


                        Button_plantillaReceta(goRestaurant) {
                            val intent = Intent(context, pWeb::class.java)
                            context.startActivity(intent)
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))


                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button_plantillaReceta2(goRestaurant2) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }

                        Button_Glossary(goGlossary) {
                            val intent = Intent(context, myGlossarryListMusht::class.java)
                            context.startActivity(intent)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun Button_Inicio_MainActivity(goInicioMainActivity : Int, onClick: () -> Unit){
    Image(
        painter = painterResource(id = R.drawable.img_return),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .clickable {
                if (goInicioMainActivity == 0) {
                    onClick()
                }
            }
    )
}

@Composable
fun Button_Receta(goReceta: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clickable {
                if (goReceta == 1) {
                    onClick()
                }
            }
    ) {

        Image(
            painter = painterResource(id = R.drawable.iconorecetas),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
fun Button_plantillaReceta(goRestaurante: Int ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clickable {
                if (goRestaurante == 2) {
                    onClick()
                }
            }
    ) {

        Image(
            painter = painterResource(id = R.drawable.iconolistarest),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

    }
}

@Composable
fun Button_Glossary(goGlossary : Int ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(170.dp)
            .clickable {
                if (goGlossary == 4) {
                    onClick()
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.glossary_img),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

    }
}
@Composable
fun Button_plantillaReceta2(goRestaurante2: Int ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clickable {
                if (goRestaurante2 == 3) {
                    onClick()
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.iconowebsetas),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

    }
}