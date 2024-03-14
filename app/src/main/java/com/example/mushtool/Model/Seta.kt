package com.example.mushtool.Model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Seta (
    var imagen: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    val tipo: String = "",
)


data class Recetas(
    @DrawableRes val imagen: Int,
    @StringRes val titulo: Int,
    @StringRes val ingrediente: Int,
    @StringRes val descripcion: Int
)