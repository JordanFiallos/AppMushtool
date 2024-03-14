package com.example.mushtool

import android.content.Intent
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.screens.pCamera
import com.example.mushtool.ui.theme.MushToolTheme

class plantillaBuscar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MushToolTheme {
        SearchApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchApp() {

    val goHome by remember { mutableIntStateOf(0) }
    val goCamera by remember { mutableIntStateOf(1) }
    val goListGrid by remember { mutableIntStateOf(2) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button_Inici_MainActivity(goHome){
                            val intent = Intent(context, MainActivity ::class.java)
                            context.startActivity(intent)
                        }

                        Text(
                            text = "Buscar",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color (android.graphics.Color.rgb(253, 232, 216)),
                    titleContentColor = Color ( 242, 217, 166),
                    navigationIconContentColor = Color ( 242, 217, 166),
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
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

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(130.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Button_Camera(goCamera) {
                        val intent = Intent(context, pCamera ::class.java)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Encontrar seta",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button_plantillaBuscar(goListGrid) {
                        val intent = Intent(context, pListGrid ::class.java)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Que tipo de seta es?",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier
                            .offset(x = 15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Button_Inici_MainActivity(goHome : Int, onClick: () -> Unit){
    Image(
        painter = painterResource(id = R.drawable.img_return),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .clickable {
                if (goHome == 0) {
                    onClick()
                }
            }
    )
}

@Composable
fun Button_Camera(goCamera: Int ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clickable {
                if (goCamera == 1) {
                    onClick()
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.mushroom_search_found),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = (-15).dp)
        )
    }
}

@Composable
fun Button_plantillaBuscar(goListGrid: Int ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clickable {
                if (goListGrid == 2) {
                    onClick()
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.mushroom_search_kind),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

    }
}