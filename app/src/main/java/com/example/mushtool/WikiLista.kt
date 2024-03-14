package com.example.mushtool

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mushtool.Model.Seta
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DataViewModel : ViewModel() {

    private val _setaFirestore = MutableStateFlow<List<Seta>>(emptyList())
    val setaFirestore: StateFlow<List<Seta>> = _setaFirestore

    private val db = Firebase.firestore

    private val _selectedSeta = MutableStateFlow<Seta?>(null)
    val selectedSeta: StateFlow<Seta?> = _selectedSeta


    init {
        getDataFromFireStore()
    }

    private fun getDataFromFireStore() {
        db.collection("Wikilista")
            .get()
            .addOnSuccessListener { documents ->
                val lsetas = mutableListOf<Seta>()
                for (document in documents) {
                    val lseta = document.toObject<Seta>()
                    lsetas.add(lseta)
                }

                val lsetasOrdenadas = lsetas.sortedBy { it.nombre }

                _setaFirestore.value = lsetasOrdenadas
                Log.d("Cargada lista", lsetasOrdenadas.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error al obtener el documento: ", exception)
            }
    }

}