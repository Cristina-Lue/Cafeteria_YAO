package com.example.coffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.adapters.ProductosAdapter
import com.example.coffee.services.ProductosService
import com.example.coffee.fragments.DetalleProductoFragment
import com.example.coffee.services.FavoritosService
import com.example.coffee.services.CarritoService


class PlatillosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewProductos)

        configurarRecyclerView()
        cargarPlatillos()
    }

    private fun configurarRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    private fun cargarPlatillos() {
        val platillos = ProductosService.obtenerPlatillos()

        adapter = ProductosAdapter(
            productos = platillos,
            onProductoClick = { producto ->
                mostrarDetallesProducto(producto)
            },
            onFavoritoClick = { producto ->
                agregarAFavoritos(producto)
            },
            onCarritoClick = { producto ->
                agregarAlCarrito(producto)
            }
        )

        recyclerView.adapter = adapter
    }

    private fun mostrarDetallesProducto(producto: com.example.coffee.models.Producto) {
        val detalleFragment = com.example.coffee.fragments.DetalleProductoFragment.newInstance(producto)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detalleFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun agregarAFavoritos(producto: com.example.coffee.models.Producto) {
        FavoritosService.agregarAFavoritos(producto)
        Toast.makeText(requireContext(), "Agregado a favoritos: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun agregarAlCarrito(producto: com.example.coffee.models.Producto) {
        CarritoService.agregarAlCarrito(producto)
        Toast.makeText(requireContext(), "Agregado al carrito: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }
}