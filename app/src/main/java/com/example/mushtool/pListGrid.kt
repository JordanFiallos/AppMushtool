package com.example.mushtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mushtool.navigation.SetaNav
import com.example.mushtool.ui.theme.MushToolTheme

class pListGrid : ComponentActivity() {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navControler = rememberNavController()

            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    SetaNav(navController = navController)
                   // ListaSetasApp()
                }
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    alpha = 0.2f,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}
/*
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
fun ListaSeta() {
    val lista = Repositorio().lista
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            stickyHeader {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .padding(0.dp),
                    text = "listas de setas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            items(lista) { seta ->
                CardCustum(seta = seta, navController)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MushToolTheme {
        Greeting3("Android")
fun CardCustum(seta: Seta){
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(1000)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            )) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        painter = painterResource(id = seta.imagen),
                        contentDescription = "Soy seta1 ",
                        contentScale = ContentScale.Fit
                    )
                    Text(seta.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier .weight(1f))
                    IconButton(onClick = {
                        visible = !visible
                    }) {
                        Icon(imageVector = if(visible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Icon" )
                    }
                }
                AnimatedVisibility(visible = visible,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ){
                    Text(text = seta.descripcion)
                }
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.End),
                    visible = visible,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ){
                    Button(onClick = {
                        navController.navigate(Screen.Detalle.route){
                            launchSingleTop=true
                        }
                    }) {
                       // Text(text = "Leer mas...")
                        Text(text = stringResource(R.string.boton_2da_pantalla))
                    }
                }
            }
        }
    }
}*/