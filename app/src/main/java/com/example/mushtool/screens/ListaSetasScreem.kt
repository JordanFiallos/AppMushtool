package com.example.mushtool.screens

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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mushtool.DataViewModel
import com.example.mushtool.Model.Seta
import com.example.mushtool.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaSetasApp(navController: NavHostController) {
    val viewModel = viewModel<DataViewModel>()
    val setaFirestore by viewModel.setaFirestore.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "MIS SETAS",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { content ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(content)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondoapp),
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MyMushroomsList(setaFirestore, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMushroomsList(listaSeta: List<Seta>, navController: NavHostController) {
    var mostrarToxicas by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Row {
                        Text(text = "Lista de setas ", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(32.dp))
                        Switch(
                            checked = mostrarToxicas,
                            onCheckedChange = { mostrarToxicas = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.Red)
                        )
                    }
                    }

            )
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Mostrar Setas Tóxicas")

            }

            LazyVerticalGrid(
                GridCells.Fixed(2),
                modifier = Modifier
                    .padding(it)
            ) {
                items(listaSeta) { mushroom ->
                    if (mostrarToxicas) {
                        // Mostrar solo setas tóxicas
                        if (mushroom.tipo == "toxico") {
                            CardCustum(mushroom, navController)
                        }
                    } else {
                        // Mostrar todas las setas
                        CardCustum(mushroom, navController)
                    }
                }
            }
        }
    }
}
@Composable
fun MisSetasItems(navController: NavHostController, nombre: String?) {
    val mushroom = Seta()
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
                painter = rememberAsyncImagePainter(mushroom.imagen),
                contentDescription = "Mushroom Image",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Imagen: ${mushroom.imagen}")
                Text(text = "Nombre:  ${mushroom.nombre}")
                Text(text = "Tipo:  ${mushroom.tipo}")
                Text(text = "Descripción: ${mushroom.descripcion}")
            }
        }
    }

}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaSetasApp(navController: NavHostController) {
    val viewModel = viewModel<DataViewModel>()
    val setaFirestore by viewModel.setaFirestore.collectAsState()


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
                            text = "MIS SETAS",
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
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(vertical = 1.dp, horizontal = 3.dp)
                        .alpha(1.0f)
                        .background(Color (android.graphics.Color.rgb(253, 232, 216)))
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MyMushroomsList(setaFirestore, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMushroomsList(listaSeta: List<Seta>, navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color (android.graphics.Color.rgb(253, 232, 216)),
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = { Text(text = "Lista de setas ", fontWeight = FontWeight.Bold) }

            )
        }
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier
                .padding(it)
        ) {
            items(listaSeta) { mushroom ->
                CardCustum(mushroom, navController)
            }
        }
    }
}

@Composable
fun MisSetasItems(navController: NavHostController, nombre: String?) {
    val mushroom = Seta()
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
                painter = rememberAsyncImagePainter(mushroom.imagen),
                contentDescription = "Mushroom Image",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Imagen: ${mushroom.imagen}")
                Text(text = "Nombre:  ${mushroom.nombre}")
                Text(text = "Tipo:  ${mushroom.tipo}")
                Text(text = "Descripción: ${mushroom.descripcion}")
                }
            }
        }

    }
*/

