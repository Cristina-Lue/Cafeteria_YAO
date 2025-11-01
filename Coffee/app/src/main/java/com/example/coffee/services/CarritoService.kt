package com.example.coffee.services

import com.example.coffee.models.Producto

object CarritoService {

    data class ItemCarrito(
        val producto: Producto,
        var cantidad: Int = 1
    )

    private val carrito: MutableList<ItemCarrito> = mutableListOf()
    private val listeners: MutableList<(List<ItemCarrito>) -> Unit> = mutableListOf()

    fun agregarAlCarrito(producto: Producto) {
        val itemExistente = carrito.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            itemExistente.cantidad++
        } else {
            carrito.add(ItemCarrito(producto, 1))
        }
        notificarCambios()
    }

    fun eliminarDelCarrito(productoId: String) {
        carrito.removeAll { it.producto.id == productoId }
        notificarCambios()
    }

    fun actualizarCantidad(productoId: String, nuevaCantidad: Int) {
        val item = carrito.find { it.producto.id == productoId }
        if (nuevaCantidad <= 0) {
            eliminarDelCarrito(productoId)
        } else {
            item?.cantidad = nuevaCantidad
            notificarCambios()
        }
    }

    fun obtenerCarrito(): List<ItemCarrito> {
        return carrito.toList()
    }

    fun calcularTotal(): Double {
        return carrito.sumOf { it.producto.precio * it.cantidad }
    }

    fun limpiarCarrito() {
        carrito.clear()
        notificarCambios()
    }

    fun obtenerCantidadTotal(): Int {
        return carrito.sumOf { it.cantidad }
    }

    // Sistema de notificación para actualizar la UI
    fun agregarListener(listener: (List<ItemCarrito>) -> Unit) {
        listeners.add(listener)
    }

    fun removerListener(listener: (List<ItemCarrito>) -> Unit) {
        listeners.remove(listener)
    }

    private fun notificarCambios() {
        val carritoActual = obtenerCarrito()
        listeners.forEach { it(carritoActual) }
    }
}