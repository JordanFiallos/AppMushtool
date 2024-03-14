package com.example.mushtool.Glosari

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class glossaryMusht :  ViewModel() {

    private val _glaosariFirestore = MutableStateFlow<List<glosarioSetas>>(emptyList())
    val glosariFirestore: MutableStateFlow<List<glosarioSetas>> = _glaosariFirestore

    private val db = Firebase.firestore

    init {
        fetchGlosariFromFirestore()
    }

    private fun fetchGlosariFromFirestore() {
        db.collection("Glosario")
            .get()
            .addOnSuccessListener { documents ->
                val glossary = mutableListOf<glosarioSetas>()
                for (document in documents) {
                    val glossaryDoc = document.toObject<glosarioSetas>()
                    glossary.add(glossaryDoc)
                }
                _glaosariFirestore.value = glossary
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error al obtener el documento: ", exception)
            }
    }


    data class glosarioSetas (
        val Name: String? = null,
        val Description: String? = null
    )

}