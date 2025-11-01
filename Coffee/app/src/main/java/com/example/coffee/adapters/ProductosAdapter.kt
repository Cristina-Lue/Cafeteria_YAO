package com.example.coffee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.models.Producto
import com.example.coffee.services.FavoritosService

class ProductosAdapter(
    private val productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit,
    private val onFavoritoClick: (Producto) -> Unit,
    private val onCarritoClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        private val ivProducto: ImageView = itemView.findViewById(R.id.ivProducto)
        private val ivFavorito: ImageView = itemView.findViewById(R.id.ivFavorito)
        private val ivCarrito: ImageView = itemView.findViewById(R.id.ivCarrito)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvPrecio.text = "$${producto.precio}"
            ivProducto.setImageResource(producto.imagenRes)


            val esFavorito = FavoritosService.esFavorito(producto.id)
            if (esFavorito) {
                ivFavorito.setImageResource(R.drawable.ic_favorite)
            } else {
                ivFavorito.setImageResource(R.drawable.ic_favorite_border)
            }

            itemView.setOnClickListener {
                onProductoClick(producto)
            }

            ivFavorito.setOnClickListener {
                onFavoritoClick(producto)

                val nuevoEstado = !FavoritosService.esFavorito(producto.id)
                if (nuevoEstado) {
                    ivFavorito.setImageResource(R.drawable.ic_favorite)
                } else {
                    ivFavorito.setImageResource(R.drawable.ic_favorite_border)
                }
            }

            ivCarrito.setOnClickListener {
                onCarritoClick(producto)
            }
        }
    }
}