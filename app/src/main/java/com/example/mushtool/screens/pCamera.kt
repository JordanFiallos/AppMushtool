package com.example.mushtool.screens

import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.R
import com.example.mushtool.ui.theme.MushToolTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.ui.layout.ContentScale
import com.example.mushtool.LGridSelect
import com.example.mushtool.Model.logicCamera
import com.example.mushtool.plantillaBuscar

class pCamera : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CameraApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MushToolTheme {
        CameraApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraApp() {

    val goIniciplantillaBuscar by remember { mutableStateOf(0) }
    val goCamera = logicCamera
    val listSetas by remember { mutableStateOf(1) }
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
                        Button_Inici_plantillaBuscar(goIniciplantillaBuscar) {
                            val intent = Intent(context, plantillaBuscar::class.java)
                            context.startActivity(intent)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Mushroom Found",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color ( rgb(253, 232, 216)),
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
                .padding(containt),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    alpha = 0.7f,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop

                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Button_Img_Camera(goCamera) {
                        val intent = Intent(context, logicCamera::class.java)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Pulsa para hacer una foto",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer (modifier = Modifier.height(30.dp))

                    Button_Img_ListSetas(listSetas) {
                        val intent = Intent(context, LGridSelect ::class.java)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Pulsa para elegir el tipo..",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.displaySmall

                    )

                }
            }
        }
    }

}

@Composable
fun Button_Inici_plantillaBuscar(goIniciplantillaBuscar: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.img_return),
        contentDescription = null,
        modifier = Modifier
            .clickable {
                if (goIniciplantillaBuscar == 0) {
                    onClick()
                }
            }
            .size(50.dp)
    )
}

@Composable
fun Button_Img_Camera(goCamera: logicCamera.Companion, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.camera_icon),
        contentDescription = "Camera",
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(1.dp)
            .height(150.dp)
            .width(150.dp)
    )
}


@Composable
fun <int> Button_Img_ListSetas(goList: int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.setabuscargrid),
        contentDescription = "Lista",
        modifier = Modifier
            .clickable {
                if(goList == 1){
                    onClick()
                }
            }
            .padding(1.dp)
            .height(150.dp)
            .width(150.dp)
    )
}