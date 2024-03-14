package com.example.mushtool

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mushtool.ui.theme.MushToolTheme
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.views.MapView
import com.example.mushtool.map.MapsMush

class Mymushrooms : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MushToolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MymushroomsApp()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {

    MushToolTheme {
        MymushroomsApp()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MymushroomsApp() {

    val viewModel = viewModel<PhotoSizeViewModel>()

    val goIniciMainActivity by remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    val mushroomViewModel = viewModel<PhotoSizeViewModel>()

    var selectedOption by remember { mutableStateOf(menuMymushrooms.SAVEIMAGE) }

    val photosFirestore by viewModel.photosFirestore.collectAsState()

    //Se Obtine el ID sel usuario en la sessión
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Filtra las fotos dependiendo del usuario
    // Filtra las fotos dependiendo del usuario
    var userPhotos by remember { mutableStateOf(emptyList<MyMushroom>()) }

    if (currentUser != null) {
        userPhotos = photosFirestore.filter { it.userId == currentUser.uid }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    {
                        Button_Inici_MainActivity(goIniciMainActivity){
                            val intent = Intent(context, MainActivity ::class.java)
                            context.startActivity(intent)
                        }

                        Text(
                            text = "MIS SETAS",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(16.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                        }
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

    ) {containt ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(containt)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp4),
                    contentDescription = null,
                    alpha = 0.7f,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Listado",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.06f).clickable {
                                selectedOption = menuMymushrooms.SAVEIMAGE
                            }
                        )

                        Text(
                            text = "Mapa",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.06f).clickable {
                                selectedOption = menuMymushrooms.MAPA
                            }
                        )
                    }

                    //MapView con el contexto
                    val mapView = MapView(context)

                    // Recursos y DisplayMetrics
                    val resources = context.resources
                    val dm = resources.displayMetrics

                    var editedMushroom: MyMushroom? by remember { mutableStateOf(null) }

                    when (selectedOption) {
                        menuMymushrooms.SAVEIMAGE -> {
                            MyMushroomsList(
                                mushrooms = userPhotos,
                                onEditMushroom = { mushroom -> editedMushroom = mushroom },
                                onDeleteMushroom = { mushroom -> mushroomViewModel.deleteMushroom(mushroom) }
                            )
                        }
                        menuMymushrooms.MAPA -> {
                            MapsMush(photos = userPhotos, mapView = mapView, dm = dm)
                        }
                    }

                    // Después del when statement
                    editedMushroom?.let { mushroom ->
                        mushroom.mComment?.let {
                            EditCommentDialog(
                                currentComment = it,
                                onConfirm = { newComment ->
                                    // Aquí llamamos a la función del ViewModel para actualizar el comentario en Firestore
                                    mushroomViewModel.updateMushroomComment(mushroom, newComment)
                                    editedMushroom = null
                                },
                                onDismiss = { editedMushroom = null }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyMushroomsList(
    mushrooms: List<MyMushroom>,
    onEditMushroom: (MyMushroom) -> Unit,
    onDeleteMushroom: (MyMushroom) -> Unit
) {
    LazyColumn {
        items(mushrooms) { mushroom ->
            MyMushroomItem(
                mushroom = mushroom,
                onEditMushroom = { onEditMushroom(mushroom) },
                onDeleteMushroom = { onDeleteMushroom(mushroom) }
            )
        }
    }
}

@Composable
fun MyMushroomItem(
    mushroom: MyMushroom,
    onEditMushroom: (MyMushroom) -> Unit,
    onDeleteMushroom: (MyMushroom) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp, horizontal = 3.dp)
                .shadow(5.dp, shape = RoundedCornerShape(18.dp), clip = true)
            .background(Color(215, 190, 162))
        ) {
            MushroomImage(
                painter = rememberAsyncImagePainter(mushroom.mPhotoUri),
                contentDescription = "Mushroom Image",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Id: ${mushroom.mId}")
                Text(text = "Tipo: ${mushroom.mKind}")
                Text(text = "Comentario: ${mushroom.mComment}")
                Text(text = "Fecha: ${formatDate(mushroom.mDate)}")
                Text(text = "Posición:")
                Text(text = "Latitud: ${mushroom.mLatitude}")
                Text(text = "Longitud: ${mushroom.mLongitude}")

                // Botones para editar y eliminar
                Row {
                    Button(
                        onClick = { onEditMushroom(mushroom) },
                        colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
                        modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
                    ) {
                        Text("Editar", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Envuelve el botón en un AlertDialog
                    AlertDialogDelate(onDeleteMushroom = onDeleteMushroom, mushroom = mushroom)
                }
            }
        }
    }
}

@Composable
fun AlertDialogDelate(
    onDeleteMushroom: (MyMushroom) -> Unit,
    mushroom: MyMushroom
) {
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text("Eliminar Foto") },
            text = { Text("¿Estás seguro de que deseas eliminar esta foto?") },
            confirmButton = {
                Button(
                    onClick = { onDeleteMushroom(mushroom); openDialog.value = false },
                    colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
                    modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
                ) {
                    Text("Sí", color = Color.Black)
                }
            },
            dismissButton = {
                Button(
                    onClick = { openDialog.value = false },
                    colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
                    modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
                ) {
                    Text("No", color = Color.Black)
                }
            }
        )
    }
    // Botón que muestra el diálogo
    Button(
        onClick = { openDialog.value = true },
        colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
        modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
    ) {
        Text("Eliminar", color = Color.Black)
    }
}

// Diálogo para editar el comentario
@Composable
fun EditCommentDialog(
    currentComment: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var comment by remember { mutableStateOf(currentComment) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Editar Comentario") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(comment)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
                modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
            ) {
                Text("Guardar", color = Color.Black)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.rgb(229, 211, 195))),
                modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = true)
            ) {
                Text("Cancelar", color = Color.Black)
            }
        },
        text = {
            TextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Nuevo Comentario") }
            )
        }
    )
}

//Dialogo para expandir la imagen
@Composable
fun MushroomImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
                .clickable {
                    showDialog = true
                }
        )

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painter,
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

// Función para formatear la fecha
fun formatDate(timestamp: Timestamp?): String {
    if (timestamp == null) return ""
    val date = timestamp.toDate()
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault())
    return sdf.format(date)
}

