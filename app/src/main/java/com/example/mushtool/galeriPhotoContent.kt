package com.example.mushtool

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mushtool.Model.Seta
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun Galeri_Photo_Content(
    bitmaps: List<PhotoWithComment>,
    modifier: Modifier = Modifier,
    onPhotoSelected: (PhotoWithComment) -> Unit,
    viewModel: PhotoSizeViewModel
) {
    val selectedPhotoToShowComment = remember { mutableStateOf<PhotoWithComment?>(null) }
    val context = LocalContext.current

    if (bitmaps.isEmpty()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Todavía no hay fotos")
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(bitmaps) { photoWithComment ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            selectedPhotoToShowComment.value = photoWithComment
                        }
                ) {
                    Image(
                        bitmap = photoWithComment.bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }

    // Muestra el cuadro de comentarios si selectedPhotoToShowComment no es nulo
    selectedPhotoToShowComment.value?.let { photoWithComment ->
        CommentBox(
            onCommentEntered = { comment, setaName ->
                val updatedPhoto = PhotoWithComment(
                    photoWithComment.bitmap,
                    comment = comment,
                )
                onPhotoSelected(updatedPhoto)
                selectedPhotoToShowComment.value = null
                ConvertBitmapToJpegAndSave(updatedPhoto.bitmap, context, comment, viewModel, setaName)
            }
        )
    }
}

fun ConvertBitmapToJpegAndSave(
    bitmap: Bitmap,
    context: Context,
    comment: String,
    viewModel: PhotoSizeViewModel,
    setaName: String
): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val displayName = "IMG_$comment$timeStamp.jpg"

    val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    if (externalFilesDir != null) {
        try {
            val file = File(externalFilesDir, displayName)

            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            outputStream.close()

            Toast.makeText(
                context,
                "Imagen guardada en el directorio de la aplicación",
                Toast.LENGTH_SHORT
            ).show()

            val photoURI = Uri.fromFile(file)
            val locationInfo = getLocationInfo(context)

            val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid

            val timeStampFirebase = Timestamp(Date())

            val mushroom = MyMushroom(
                mId = generateUniqueId(),
                mKind = setaName,
                mComment = comment,
                mDate = timeStampFirebase,
                mLatitude = locationInfo?.latitude,
                mLongitude = locationInfo?.longitude,
                mPhotoId = displayName,
                mPhotoUri = photoURI.toString(),
                userId = uid
            )

            saveToFirestore(mushroom)
            uploadImageFirebase(file, displayName)

            return photoURI

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error al guardar la imagen: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    } else {
        Toast.makeText(
            context,
            "Error obteniendo el directorio de la aplicación",
            Toast.LENGTH_SHORT
        ).show()
    }

    return null
}

fun uploadImageFirebase(file: File, displayName: String) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val imageRef = storageRef.child("images/$displayName")

    val uploadTask = imageRef.putFile(Uri.fromFile(file))

    uploadTask.addOnSuccessListener {
        imageRef.downloadUrl.addOnCompleteListener { urlTask ->
            if (urlTask.isSuccessful) {
                val downloadUrl = urlTask.result.toString()
                Log.d("Upload Success", "Image uploaded successfully. Download URL: $downloadUrl")
            } else {
                Log.e("Download URL Failure", "Failed to get download URL", urlTask.exception)
            }
        }
    }.addOnFailureListener { exception ->
        Log.e("Upload Failure", "Failed to upload image", exception)
    }
}

private fun saveToFirestore(mushroom: MyMushroom) {
    val db = Firebase.firestore

    db.collection("profiles")
        .add(mushroom)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

private fun generateUniqueId(): Long {
    return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
}

fun extractIdFromUri(uri: Uri): Long {
    val fileName = uri.lastPathSegment
    val idString = fileName?.substringAfter("IMG_")?.substringBefore(".jpg")
    return idString?.toLongOrNull() ?: -1
}

fun getLocationInfo(context: Context): Location? {
    return try {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        location?.let {
            Location("").apply {
                latitude = it.latitude
                longitude = it.longitude
            }
        }
    } catch (e: SecurityException) {
        Log.e("PhotoSizeViewModel", "Permiso de ubicación no concedido: ${e.message}")
        null
    } catch (e: Exception) {
        Log.e("PhotoSizeViewModel", "Error al obtener información de ubicación: ${e.message}")
        null
    }
}

@Composable
fun CommentBox(onCommentEntered: (String, String) -> Unit) {
    var commentText by remember { mutableStateOf("") }
    val desplegableViewModel: DataViewModel = DataViewModel()
    val opciones by desplegableViewModel.setaFirestore.collectAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var seleccionada: Seta? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    val intent = Intent(context, LGridSelect ::class.java)
                    context.startActivity(intent)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Seleccionar Seta")
            }

            if (expanded) {
                LazyColumn {
                    items(opciones) { seta ->
                        Text(
                            text = seta.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    seleccionada = seta
                                    expanded = false
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }

            Text(
                text = "Seta Seleccionada: ${seleccionada?.nombre.orEmpty()}",
                modifier = Modifier.padding(16.dp)
            )
        }

        TextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text("Ingresa tu comentario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                onCommentEntered(commentText, seleccionada?.nombre.orEmpty())
                commentText = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar")
        }
    }
}
