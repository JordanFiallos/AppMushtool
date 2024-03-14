package com.example.mushtool.screens

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

// En languageViewModel
// En languageViewModel
class languageViewModel(context: Context) : ViewModel() {
    private val preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE)
    val language = MutableLiveData<String>() // Cambiar a MutableLiveData

    init {
        language.value = preferences.getString("selectedLanguage", "en") ?: "en"
    }

    fun changeLanguage(language: String) {
        preferences.edit().putString("selectedLanguage", language).apply()
        this.language.value = language // Actualizar el valor
    }
}


