package com.example.coffee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.adapters.FavoritosAdapter
import com.example.coffee.services.FavoritosService
import com.example.coffee.fragments.DetalleProductoFragment
import com.example.coffee.services.CarritoService


class FavoritosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvVacio: TextView
    private lateinit var adapter: FavoritosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_favoritos_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFavoritos)
        tvVacio = view.findViewById(R.id.tvFavoritosVacio)

        configurarRecyclerView()
        cargarFavoritos()
    }

    private fun configurarRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun cargarFavoritos() {
        FavoritosService.obtenerFavoritos { favoritos ->
            if (favoritos.isEmpty()) {
                tvVacio.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                tvVacio.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                adapter = FavoritosAdapter(
                    productos = favoritos,
                    onProductoClick = { producto ->
                        mostrarDetallesProducto(producto)
                    },
                    onCarritoClick = { producto ->
                        agregarAlCarrito(producto)
                    },
                    onEliminarClick = { producto ->
                        eliminarDeFavoritos(producto)
                    }
                )
                recyclerView.adapter = adapter
            }
        }
    }

    private fun mostrarDetallesProducto(producto: com.example.coffee.models.Producto) {
        val detalleFragment = com.example.coffee.fragments.DetalleProductoFragment.newInstance(producto)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detalleFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun agregarAlCarrito(producto: com.example.coffee.models.Producto) {
        CarritoService.agregarAlCarrito(producto)
        Toast.makeText(requireContext(), "Agregado al carrito: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun eliminarDeFavoritos(producto: com.example.coffee.models.Producto) {
        FavoritosService.eliminarDeFavoritos(producto.id)
        cargarFavoritos() // Recargar la lista
        Toast.makeText(requireContext(), "Eliminado de favoritos: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }
}