package com.example.coffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coffee.R
import com.example.coffee.models.Producto
import com.example.coffee.services.CarritoService
import android.widget.Toast

class DetalleProductoFragment : Fragment() {

    companion object {
        private const val ARG_PRODUCTO = "producto"

        fun newInstance(producto: Producto): DetalleProductoFragment {
            val fragment = DetalleProductoFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCTO, producto) // Cambiado a putParcelable
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            producto = it.getParcelable(ARG_PRODUCTO) ?: Producto() // Cambiado a getParcelable
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detalle_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivProducto: ImageView = view.findViewById(R.id.ivProductoDetalle)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreDetalle)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioDetalle)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionDetalle)
        val tvIngredientes: TextView = view.findViewById(R.id.tvIngredientesDetalle)
        val btnAgregarCarrito: Button = view.findViewById(R.id.btnAgregarCarrito)

        // Por ahora usamos un placeholder, después pondremos imágenes reales
        ivProducto.setBackgroundColor(0xFFF5F5F5.toInt())
        if (producto.imagenRes is Int) {
            ivProducto.setImageResource(producto.imagenRes)
        }
        tvNombre.text = producto.nombre
        tvPrecio.text = "$${producto.precio}"
        tvDescripcion.text = producto.descripcion
        tvIngredientes.text = obtenerIngredientes(producto)

        btnAgregarCarrito.setOnClickListener {
            // Agregar al carrito
            agregarAlCarrito()
        }
    }

    private fun obtenerIngredientes(producto: Producto): String {
        return when (producto.nombre) {
            "Frapuccino" -> "• Café espresso\n• Hielo\n• Leche\n• Azúcar\n• Crema batida"
            "Capuccino" -> "• Café espresso\n• Leche vaporizada\n• Espuma de leche\n• Canela opcional"
            "Espresso" -> "• Granos de café molidos\n• Agua caliente"
            "Matcha" -> "• Té matcha en polvo\n• Leche vaporizada\n• Miel o azúcar"
            "Tartaleta" -> "• Masa quebrada\n• Crema pastelera\n• Frutas frescas\n• Gelatina"
            "Alfajores" -> "• Galletas\n• Dulce de leche\n• Coco rallado\n• Chocolate"
            "Cheescake" -> "• Galleta molida\n• Queso crema\n• Crema agria\n• Vainilla\n• Frutos rojos"
            "Tiramisu" -> "• Bizcochos de soletilla\n• Café espresso\n• Queso mascarpone\n• Cacao en polvo"
            "Sandwich" -> "• Pan integral\n• Pavo\n• Queso suizo\n• Lechuga\n• Tomate\n• Mayonesa"
            "Papas fritas" -> "• Papas\n• Aceite vegetal\n• Sal"
            "Croissant" -> "• Masa de hojaldre\n• Mantequilla\n• Huevo para barnizar"
            "Hamburguesa" -> "• Pan de hamburguesa\n• Carne de res\n• Queso cheddar\n• Lechuga\n• Tomate\n• Salsas"
            else -> "Ingredientes no disponibles"
        }
    }

    private fun agregarAlCarrito() {
        CarritoService.agregarAlCarrito(producto)


        view?.let {
            Toast.makeText(it.context, "Agregado al carrito: ${producto.nombre}", Toast.LENGTH_SHORT).show()
        }

        requireActivity().supportFragmentManager.popBackStack()
    }

}
