package com.example.mushtool.Restaurante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mushtool.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRestauranteFApp(navController: NavHostController) {
    val viewModel = viewModel<DataRestauranteFViewModel>()
    val restauranteFirestore by viewModel.restauranteFirestore.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Text(
                            text = "RESTAURANTES",
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
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    alpha = 0.2f,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MiRestauranteFList(restauranteFirestore, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiRestauranteFList(listaRestauranteF: List<DataRestauranteF>, navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color (android.graphics.Color.rgb(253, 232, 216)),
                    titleContentColor = Color.Black,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer

                ),
                title = { Text(text = "Lista de Restaurante ", fontWeight = FontWeight.Bold) }

            )
        }
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(1),
            modifier = Modifier
                .padding(it)
        ) {
            items(listaRestauranteF) { __restauranteF ->
                CardCustum(__restauranteF, navController)
            }
        }
    }
}

@Composable
fun MisRestaurantesFItems(navController: NavHostController) {
    val restaurante = DataRestauranteF()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(restaurante.imagen),
                contentDescription = "Receta Image",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Imagen: ${restaurante.imagen}")
                Text(text = "NOMBRE:  ${restaurante.nombre}")
                Text(text = "DIRECCIÓN:  ${restaurante.direccion}")
                Text(text = "HORARIO: ${restaurante.horario}")
                }
            }
        }

    }
