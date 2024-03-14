package com.example.mushtool.Model

import com.example.mushtool.R


class RecetaData() {
    fun loadRecetas(): List<Recetas> {
        return listOf<Recetas>(
            Recetas(R.drawable.receta1,R.string.titulo_receta1, R.string.ingrediente_receta1, R.string.descripcion_receta1),
            Recetas(R.drawable.receta2,R.string.titulo_receta2, R.string.ingrediente_receta2, R.string.descripcion_receta2),
            Recetas(R.drawable.receta3,R.string.titulo_receta3, R.string.ingrediente_receta3, R.string.descripcion_receta3),
            Recetas(R.drawable.receta4,R.string.titulo_receta4, R.string.ingrediente_receta4, R.string.descripcion_receta4)

        )
    }
}