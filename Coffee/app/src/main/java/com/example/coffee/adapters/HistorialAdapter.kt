package com.example.coffee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.Pedido

class HistorialAdapter(
    private val pedidos: List<Pedido>
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount(): Int = pedidos.size

    inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFechaHistorial)
        private val tvTotal: TextView = itemView.findViewById(R.id.tvTotalHistorial)
        private val tvProductos: TextView = itemView.findViewById(R.id.tvProductosHistorial)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstadoHistorial)

        fun bind(pedido: Pedido) {
            tvFecha.text = "Fecha: ${pedido.fecha}"
            tvTotal.text = "Total: $${pedido.total}"
            tvEstado.text = "Estado: ${pedido.estado}"


            val productosText = pedido.productos.joinToString("\n") { producto ->
                "• ${producto.nombre} x${producto.cantidad} - $${producto.precio * producto.cantidad}"
            }
            tvProductos.text = productosText
        }
    }
}