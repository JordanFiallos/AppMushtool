package com.example.mushtool

import android.content.ContentValues
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.ZoneId

@Serializable
data class PhotoWithComment(
    val bitmap: Bitmap,
    val comment: String,
)

class PhotoSizeViewModel : ViewModel() {

    private val _photos = MutableStateFlow<List<PhotoWithComment>>(emptyList())
    var photos = _photos.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap, comment: String) {
        val photoWithComment = PhotoWithComment(bitmap, comment)
        _photos.value += photoWithComment
    }

    private val _originalPhotosFirestore = MutableStateFlow<List<MyMushroom>>(emptyList())
    val _photosFirestore = MutableStateFlow<List<MyMushroom>>(emptyList())
    val photosFirestore: StateFlow<List<MyMushroom>> = _photosFirestore


    private val db = Firebase.firestore

    init {
        fetchPhotosFromFirestore()
    }

    private fun fetchPhotosFromFirestore() {
        db.collection("profiles")
            .get()
            .addOnSuccessListener { documents ->
                val photos = mutableListOf<MyMushroom>()
                for (document in documents) {
                    val mushroom = document.toObject<MyMushroom>()
                    photos.add(mushroom)
                }
                _photosFirestore.value = photos
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error al obtener el documento: ", exception)
            }
    }

    fun updateMushroomComment(mushroom: MyMushroom, newComment: String) {
        // Encuentra la seta en la lista de fotos de Firestore y actualiza su comentario
        val updatedPhotos = _photosFirestore.value.map { existingMushroom ->
            if (existingMushroom.mPhotoUri == mushroom.mPhotoUri) {
                existingMushroom.copy(mComment = newComment)
            } else {
                existingMushroom
            }
        }
        _photosFirestore.value = updatedPhotos

        // Busca la imagen por su URI y actualiza el comentario en Firestore
        db.collection("profiles")
            .whereEqualTo("mphotoUri", mushroom.mPhotoUri)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.update("mcomment", newComment)
                        .addOnSuccessListener {
                            Log.d("PhotoSizeViewModel", "Comentario actualizado con éxito en Firestore")
                        }
                        .addOnFailureListener { exception ->
                            Log.w("PhotoSizeViewModel", "Error al actualizar el comentario en Firestore", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PhotoSizeViewModel", "Error al buscar la imagen en Firestore", exception)
            }
    }

    fun deleteMushroom(mushroom: MyMushroom) {
        // Elimina el documento de Firestore que contiene los datos de la imagen
        db.collection("profiles")
            .whereEqualTo("mphotoUri", mushroom.mPhotoUri)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Log.d("PhotoSizeViewModel", "Documento eliminado con éxito en Firestore")
                            fetchPhotosFromFirestore()
                        }
                        .addOnFailureListener { exception ->
                            Log.w("PhotoSizeViewModel", "Error al eliminar el documento en Firestore", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PhotoSizeViewModel", "Error al buscar el documento en Firestore", exception)
            }

        // Elimina el archivo de la imagen del almacenamiento de Firebase
        val storageRef = Firebase.storage.reference
        val photoRef = storageRef.child("images/${mushroom.mPhotoId}")
        photoRef.delete()
            .addOnSuccessListener {
                Log.d("PhotoSizeViewModel", "Imagen eliminada con éxito en Storage")
                fetchPhotosFromFirestore()
            }
            .addOnFailureListener { exception ->
                Log.w("PhotoSizeViewModel", "Error al eliminar la imagen en Storage", exception)
            }
    }

    fun updatePhotosFirestore(filteredPhotos: List<MyMushroom>) {
        _photosFirestore.value = filteredPhotos
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun applyDateFilter(startDate: LocalDate, endDate: LocalDate) {
        val originalPhotos = _originalPhotosFirestore.value
        val filteredByDate = originalPhotos.filter { photo ->
            val photoDate = photo.mDate?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            if (photoDate != null) {
                val nextDay = endDate.plusDays(1)
                !photoDate.isBefore(startDate) && photoDate.isBefore(nextDay)
            } else {
                false
            }
        }
        _photosFirestore.value = filteredByDate.toList()  // Crear una nueva lista para _photosFirestore
    }
}