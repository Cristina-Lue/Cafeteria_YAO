package com.example.coffee.services

import com.example.coffee.models.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FavoritosService {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var favoritos: MutableList<Producto> = mutableListOf()

    fun agregarAFavoritos(producto: Producto) {
        val usuarioId = auth.currentUser?.uid ?: return

        // Agregar localmente
        if (!favoritos.any { it.id == producto.id }) {
            favoritos.add(producto)
        }

        // Guardar en Firestore
        db.collection("usuarios")
            .document(usuarioId)
            .collection("favoritos")
            .document(producto.id)
            .set(producto)
    }

    fun eliminarDeFavoritos(productoId: String) {
        val usuarioId = auth.currentUser?.uid ?: return

        // Eliminar localmente
        favoritos.removeAll { it.id == productoId }

        // Eliminar de Firestore
        db.collection("usuarios")
            .document(usuarioId)
            .collection("favoritos")
            .document(productoId)
            .delete()
    }

    fun obtenerFavoritos(callback: (List<Producto>) -> Unit) {
        val usuarioId = auth.currentUser?.uid ?: return callback(emptyList())

        db.collection("usuarios")
            .document(usuarioId)
            .collection("favoritos")
            .get()
            .addOnSuccessListener { documents ->
                val productos = mutableListOf<Producto>()
                for (document in documents) {
                    val producto = document.toObject(Producto::class.java)
                    productos.add(producto)
                }
                favoritos = productos.toMutableList()
                callback(productos)
            }
            .addOnFailureListener {
                callback(favoritos)
            }
    }

    fun esFavorito(productoId: String): Boolean {
        return favoritos.any { it.id == productoId }
    }
}