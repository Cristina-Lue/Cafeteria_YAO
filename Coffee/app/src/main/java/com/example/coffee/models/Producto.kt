package com.example.coffee.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val id: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val categoria: String = "",
    val imagenRes: Int = 0

) : Parcelable
