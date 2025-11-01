package com.example.coffee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.models.Producto

class FavoritosAdapter(
    private val productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit,
    private val onCarritoClick: (Producto) -> Unit,
    private val onEliminarClick: (Producto) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorito, parent, false)
        return FavoritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    inner class FavoritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreFavorito)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioFavorito)
        private val ivProducto: ImageView = itemView.findViewById(R.id.ivProductoFavorito)
        private val ivCarrito: ImageView = itemView.findViewById(R.id.ivCarritoFavorito)
        private val ivEliminar: ImageView = itemView.findViewById(R.id.ivEliminarFavorito)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvPrecio.text = "$${producto.precio}"

            // 🟩 Mostrar imagen desde drawable
            if (producto.imagenRes != 0) {
                ivProducto.setImageResource(producto.imagenRes)
            } else {
                // Imagen por defecto si no hay
                ivProducto.setImageResource(R.drawable.fondo_gradiente)
            }

            itemView.setOnClickListener { onProductoClick(producto) }
            ivCarrito.setOnClickListener { onCarritoClick(producto) }
            ivEliminar.setOnClickListener { onEliminarClick(producto) }
        }
    }
}
