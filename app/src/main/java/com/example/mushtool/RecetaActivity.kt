package com.example.mushtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mushtool.Model.RecetaData
import com.example.mushtool.Model.Recetas
import com.example.mushtool.ui.theme.MushToolTheme

class RecetaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   RecetaApp()

                }
            }
        }
    }
}

@Composable
fun RecetaList(recetaList: List<Recetas>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(recetaList) { affirmation ->
            RecetaCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun RecetaApp() {
    RecetaList(
        recetaList = RecetaData().loadRecetas(),
    )
}


@Composable
fun RecetaCard(affirmation: Recetas, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.onTertiary,
            label = ""
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Image(
                painter = painterResource(id = affirmation.imagen),
                contentDescription = stringResource(id = affirmation.titulo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.titulo),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier)
            Column {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {

                    Text(
                        text = LocalContext.current.getString(affirmation.ingrediente),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = LocalContext.current.getString(affirmation.descripcion),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecetaCardPreview() {
    RecetaCard(
        Recetas(
            R.drawable.receta1,
            R.string.titulo_receta1,
            R.string.ingrediente_receta1,
            R.string.descripcion_receta1
        )
    )
}