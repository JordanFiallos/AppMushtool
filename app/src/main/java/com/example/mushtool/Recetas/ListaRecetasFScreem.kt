package com.example.mushtool.Recetas

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRecetasFApp(navController: NavHostController) {
    val viewModel = viewModel<DataRecetasFViewModel>()
    val recetasFirestore by viewModel.recetasFirestore.collectAsState()

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
                            text = "MIS RECETAS",
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
                        MiRecetasFList(recetasFirestore, navController)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiRecetasFList(listaRecetas: List<DataRecetasF>, navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color (android.graphics.Color.rgb(253, 232, 216)),
                    titleContentColor = Color ( 242, 217, 166),
                    navigationIconContentColor = Color ( 242, 217, 166),
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = { Text(text = "Lista de las Recetas ", fontWeight = FontWeight.Bold) }

            )
        }
    ) {
        LazyVerticalGrid( GridCells.Fixed(1),
            modifier = Modifier
                .padding(it)
        ) {
            items(listaRecetas) { recetaF ->
                CardCustum(recetaF, navController)
            }
        }
    }
}

@Composable
fun MisRecetasItems(navController: NavHostController) {
    val recetaF = DataRecetasF()
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
                painter = rememberAsyncImagePainter(recetaF.imagen),
                contentDescription = "Receta Image",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Imagen: ${recetaF.imagen}")
                Text(text = "TÍTULO:  ${recetaF.titulo}")
                Text(text = "INGREDIENTE:  ${recetaF.ingrediente}")
                Text(text = "PREPARACIÓN: ${recetaF.descripcion}")
                }
            }
        }

    }
