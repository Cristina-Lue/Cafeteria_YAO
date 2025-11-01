package com.example.coffee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.services.CarritoService

class CarritoAdapter(
    private val itemsCarrito: List<CarritoService.ItemCarrito>,
    private val onCantidadChange: (String, Int) -> Unit,
    private val onEliminarClick: (String) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = itemsCarrito[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemsCarrito.size

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreCarrito)
        private val tvPrecioUnitario: TextView = itemView.findViewById(R.id.tvPrecioUnitario)
        private val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        private val tvSubtotal: TextView = itemView.findViewById(R.id.tvSubtotal)
        private val ivMenos: ImageView = itemView.findViewById(R.id.ivMenos)
        private val ivMas: ImageView = itemView.findViewById(R.id.ivMas)
        private val ivEliminar: ImageView = itemView.findViewById(R.id.ivEliminarCarrito)

        fun bind(item: CarritoService.ItemCarrito) {
            val ivImagen: ImageView = itemView.findViewById(R.id.ivImagenProductoCarrito)
            if (item.producto.imagenRes != 0) {
                ivImagen.setImageResource(item.producto.imagenRes)
            } else {
                ivImagen.setImageResource(R.drawable.fondo_gradiente)
            }

            tvNombre.text = item.producto.nombre
            tvPrecioUnitario.text = "$${item.producto.precio} c/u"
            tvCantidad.text = item.cantidad.toString()

            val subtotal = item.producto.precio * item.cantidad
            tvSubtotal.text = "$$subtotal"

            ivMenos.setOnClickListener {
                if (item.cantidad > 1) {
                    onCantidadChange(item.producto.id, item.cantidad - 1)
                }
            }

            ivMas.setOnClickListener {
                onCantidadChange(item.producto.id, item.cantidad + 1)
            }

            ivEliminar.setOnClickListener {
                onEliminarClick(item.producto.id)
            }
        }
    }
}