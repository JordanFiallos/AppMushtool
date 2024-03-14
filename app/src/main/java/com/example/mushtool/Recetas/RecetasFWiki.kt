package com.example.mushtool.Recetas

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DataRecetasFViewModel : ViewModel() {

    private val _recetasFirestore = MutableStateFlow<List<DataRecetasF>>(emptyList())
    val recetasFirestore: StateFlow<List<DataRecetasF>> = _recetasFirestore

    private val db = Firebase.firestore

    init {
        getDataRecetasFFromFireStore()
    }

    private fun getDataRecetasFFromFireStore() {
        db.collection("RecetasF")
            .get()
            .addOnSuccessListener { documents ->
                val lsetas = mutableListOf<DataRecetasF>()
                for (document in documents) {
                    val lseta = document.toObject<DataRecetasF>()
                    lsetas.add(lseta)
                }
               /* _recetasFirestore.value = lsetas
                Log.d("Cargada lista", lsetas.size.toString())*/

                val lsetasOrdenadas = lsetas.sortedBy { it.titulo }

                _recetasFirestore.value = lsetasOrdenadas
                Log.d("Cargada lista", lsetasOrdenadas.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error al obtener el documento: ", exception)
            }

    }
}
