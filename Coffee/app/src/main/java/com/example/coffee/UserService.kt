package com.example.coffee

import com.google.firebase.firestore.FirebaseFirestore

object UserService {

    private val db = FirebaseFirestore.getInstance()

    fun obtenerHistorialPedidos(usuarioId: String, callback: (List<Pedido>) -> Unit) {
        db.collection("usuarios")
            .document(usuarioId)
            .collection("pedidos")
            .get()
            .addOnSuccessListener { documents ->
                val pedidos = mutableListOf<Pedido>()
                for (document in documents) {
                    val pedido = document.toObject(Pedido::class.java)
                    pedidos.add(pedido)
                }
                callback(pedidos)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun agregarPedidoAlHistorial(usuarioId: String, pedido: Pedido) {
        db.collection("usuarios")
            .document(usuarioId)
            .collection("pedidos")
            .add(pedido)
    }
}

data class Pedido(
    val id: String = "",
    val fecha: String = "",
    val total: Double = 0.0,
    val productos: List<ProductoPedido> = emptyList(),
    val estado: String = "Completado"
)

data class ProductoPedido(
    val nombre: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)

