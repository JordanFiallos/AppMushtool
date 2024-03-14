package com.example.mushtool.Restaurante

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DataRestauranteFViewModel : ViewModel() {

    private val _restauranteFirestore = MutableStateFlow<List<DataRestauranteF>>(emptyList())
    val restauranteFirestore: StateFlow<List<DataRestauranteF>> = _restauranteFirestore

    private val db = Firebase.firestore

    init {
        getDataRestauranteFromFireStore()
    }

    private fun getDataRestauranteFromFireStore() {
        db.collection("Restaurante")
            .get()
            .addOnSuccessListener { documents ->
                val listarestaurantes = mutableListOf<DataRestauranteF>()
                for (document in documents) {
                    val listarestaurante = document.toObject<DataRestauranteF>()
                    listarestaurantes.add(listarestaurante)
                }
                _restauranteFirestore.value = listarestaurantes
                Log.d("Cargada lista Restaurante", listarestaurantes.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error al obtener el documento: ", exception)
            }

    }
}
