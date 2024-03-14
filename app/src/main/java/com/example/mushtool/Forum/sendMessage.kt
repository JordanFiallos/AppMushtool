package com.example.mushtool.Forum

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.mushtool.MyMushroom
import com.example.mushtool.PhotoSizeViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ChatScreen(chatViewModel: ChatViewModel, navController: NavController, onItemClick: (ChatMessage) -> Unit, onImageClick: (String) -> Unit) {
    val messages by chatViewModel.messages.asFlow().collectAsState(initial = emptyList())
    val selectedMessage by chatViewModel.getSelectedMessage().asFlow().collectAsState(initial = null)
    val isReplying by chatViewModel.getIsReplying().asFlow().collectAsState(initial = false)

    //Muestra el recuadro de dialogo de la lista de Seleción de fotos del chat
    val openLocationDialog = remember { mutableStateOf(false) }

    // Se obtienen datos del Firebase Storage
    val viewModelphotosFirestore = viewModel<PhotoSizeViewModel>()

    val photosFirestore by viewModelphotosFirestore.photosFirestore.collectAsState()

    // Se obtiene el UID del usuario para poder filtrar localizaciones
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    //Ayuda mostar el recuadro de Dialogo del Chat
    var showDialog by remember { mutableStateOf(false) }

    var imageUrl by remember { mutableStateOf("") }

    //Se Obtine la localización
    var selectedLocation by remember { mutableStateOf<Location?>(null) }

    var selectedMushroom: MyMushroom? by remember { mutableStateOf(null) }

    var messageText by remember { mutableStateOf("") }

    //Estado para controlar la visibilidad de las opciones
    var showFullChat by remember { mutableStateOf(false) }

    var showOptions by remember { mutableStateOf(true) }

    // Cambiar la visibilidad de las opciones
    val onShowFullChatClick: () -> Unit = {
        showOptions = !showOptions
    }

    //Expande y muestra la foto en un recuadro de Dialogo del Chat
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Imagen de la setas") },
            text = { Image(painter = rememberImagePainter(data = imageUrl), contentDescription = null) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cerrar")
                }
            }
        )
    }

    // Filtra las fotos de la lista de Seleción de fotos por el usuario actual
    if (openLocationDialog.value) {
        val currentUserPhotos = photosFirestore.filter { it.userId == currentUserUid }
        LocationSelectionDialog(
            photos = currentUserPhotos,
            onPhotoSelected = { photo ->
                selectedLocation = Location("").apply {
                    latitude = photo.mLatitude ?: 0.0
                    longitude = photo.mLongitude ?: 0.0
                }
                selectedMushroom = photo
                openLocationDialog.value = false
            },
            onDismiss = { openLocationDialog.value = false }
        )
    }

    // Mostrar la lista de mensajes y formulario para enviar mensajes
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Verificar si se deben mostrar las opciones
        if (showOptions) {
            // Botón para mostrar el chat completo
            IconButton(onClick = onShowFullChatClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Mostrar chat completo"
                )
            }
        }

        // Verificar si se debe mostrar el chat completo o parcial y renderizarlo correspondientemente
        if (showOptions || showFullChat) {
            ChatMessageList(
                messages = messages,
                photosFirestore = photosFirestore,
                onItemClick = onItemClick,
                onImageClick = onImageClick
            )
        } else {
            //Filtro para que se muestren todos los mensajes del chat
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(messages) { message ->
                    val selectedPhoto = photosFirestore.find { it.mId == message.mushroomId }
                    MessageItem(
                        message = message,
                        onItemClick = onItemClick, // Pasar la función onItemClick aquí
                        photo = selectedPhoto,
                        onImageClick = { imageUrlToShow ->
                            imageUrl = imageUrlToShow
                            showDialog = true
                        }
                    )
                }
            }
        }

        // Botón para abrir el diálogo de selección de ubicación
        Button(
            onClick = {
                openLocationDialog.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Compartir Ubicación setas"
            )
        }

        // Formulario para enviar mensajes
        ChatInputField(
            messageText = messageText,
            onMessageTextChanged = { messageText = it },
            onSendClick = {
                val serializableLocation = selectedLocation?.let { SerializableLocation(it.latitude, it.longitude) }
                val selectedMushroomId = selectedMushroom?.mId ?: ""
                chatViewModel.sendMessage(messageText, serializableLocation, selectedMushroomId.toString())
                messageText = ""
                selectedMushroom = null
            },
            onShowFullChatClick = onShowFullChatClick
        )

        // Muestra la pantalla de respuesta si estás respondiendo
        if (isReplying) {
            ReplyScreen(chatViewModel = chatViewModel)
        }

        // Muestra la pantalla de mensajes respondidos al mensaje seleccionado
        if (selectedMessage != null) {
            RepliedMessagesScreen(chatViewModel = chatViewModel)
        }
    }
}

@Composable
fun ChatMessageList(
    messages: List<ChatMessage>,
    photosFirestore: List<MyMushroom>,
    onItemClick: (ChatMessage) -> Unit,
    onImageClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(messages) { message ->
            //Filtra las fotos de setas para enviar
            val selectedPhoto = photosFirestore.find { it.mId == message.mushroomId }
            MessageItem(
                message = message,
                onItemClick = onItemClick,
                photo = selectedPhoto,
                onImageClick = onImageClick
            )
        }
    }
}

@Composable
fun MessageItem(
    message: ChatMessage,
    onItemClick: (ChatMessage) -> Unit,
    photo: MyMushroom?,
    onImageClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(message) }
    ) {
        //Muestra la lista de mensajes del chat en forma vertical
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = message.senderName, fontWeight = FontWeight.Bold)
            if (message.text.isNotBlank()) {
                Text(text = message.text, modifier = Modifier.padding(top = 4.dp))
            }

            photo?.let {
                Text(text = "LAT : ${it.mLatitude ?: " "} LON : ${it.mLongitude ?: " "}")
                Image(
                    painter = rememberAsyncImagePainter(model = it.mPhotoUri),
                    contentDescription = "Imagen de la seta",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable {
                            onImageClick(it.mPhotoUri ?: "")
                        }
                        .padding(top = 8.dp),
                )
            }
        }
    }
}
@Composable
fun ChatInputField(
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onShowFullChatClick: () -> Unit
) {
    var message by remember { mutableStateOf(messageText) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = message,
            onValueChange = { message = it; onMessageTextChanged(it) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendClick()
                    message = ""
                }
            )
        )

        // Botón para enviar mensajes
        FloatingActionButton(
            onClick = {
                onSendClick()
                message = ""
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null
            )
        }

        // Botón para mostrar el chat completo
        IconButton(onClick = onShowFullChatClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "Mostrar chat completo"
            )
        }
    }
}
@Composable
fun ReplyScreen(chatViewModel: ChatViewModel) {
    val replyText = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mostrar el mensaje original al que se está respondiendo
        val selectedMessage by chatViewModel.getSelectedMessage().asFlow().collectAsState(initial = null)
        if (selectedMessage != null) {
            Text("Responde: ${selectedMessage!!.senderName}\n${selectedMessage!!.text}")
        }

        // Campo de texto para la respuesta
        TextField(
            value = replyText.value,
            onValueChange = {
                replyText.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Respuesta") }
        )

        // Botón para enviar la respuesta
        Button(
            onClick = {
                chatViewModel.sendMessage(replyText.value.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Enviar Respuesta")
        }

        // Lista de respuestas asociadas al mensaje seleccionado
        val replies = chatViewModel.getSelectedMessageReplies()
        if (replies.isNotEmpty()) {
            Text("Responder:")
            for (reply in replies) {
                // Text("${reply.senderName}: ${reply.text}")
                Text("${reply.text}")
            }
        }
    }
}

@Composable
fun RepliedMessagesScreen(chatViewModel: ChatViewModel) {
    val replies = chatViewModel.getRepliesForSelectedMessage()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de respuestas al mensaje seleccionado
        LazyColumn {
            items(replies) { reply ->
                ReplyItem(reply = reply)
            }
        }
    }
}

@Composable
fun ReplyItem(reply: ChatMessage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = reply.senderName, fontWeight = FontWeight.Bold)
            Text(text = reply.text, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectionDialog(
    photos: List<MyMushroom>,
    onPhotoSelected: (MyMushroom) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = { onDismiss() },
        content = {
            Column {
                Text(text = "Seleccionar ubicación de Setas")
                LazyColumn {
                    items(photos) { photo ->
                        ListPhotosMushrooms(
                            photo = photo,
                            onPhotoSelected = {
                                onPhotoSelected(it)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ListPhotosMushrooms(
    photo: MyMushroom,
    onPhotoSelected: (MyMushroom) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isSelected = !isSelected
                onPhotoSelected(photo)
            }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = photo.mPhotoUri),
                contentDescription = "Imagen de la seta",
                modifier = Modifier
                    .size(64.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Color.Gray else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Nombre: ${photo.mKind}")
                Text(text = "Ubicación: ${photo.mLatitude}, - ${photo.mLongitude}")
            }
        }
    }
}
