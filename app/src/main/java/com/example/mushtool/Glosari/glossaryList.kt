package com.example.mushtool.Glosari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mushtool.R
import com.example.mushtool.ui.theme.MushToolTheme


class myGlossarryListMusht : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListGlossaryMushtool()
                }
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    alpha = 0.3f,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop

                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGlossary(){
    MushToolTheme{
        ListGlossaryMushtool()
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListGlossaryMushtool (){

    val listglossary = viewModel<glossaryMusht>()
    val listglossryFirebase by listglossary.glosariFirestore.collectAsState()


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
                            text = "GLOSARIO",
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
                        MyListGlossaryMushtools(listglossryFirebase)
                    }
                }
            }
        }
    }
}

@Composable
fun MyListGlossaryMushtools(listglossaryFirebase: List<glossaryMusht.glosarioSetas>) {

    LazyColumn {
        items(listglossaryFirebase) { glossaryM ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(text = "${glossaryM.Name}")
                        Text(text = "${glossaryM.Description}")
                    }
                }
            }
        }
    }

}