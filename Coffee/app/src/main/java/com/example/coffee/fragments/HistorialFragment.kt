package com.example.coffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.adapters.HistorialAdapter
import com.example.coffee.UserService
import com.google.firebase.auth.FirebaseAuth

class HistorialFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvVacio: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewHistorial)
        tvVacio = view.findViewById(R.id.tvHistorialVacio)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cargarHistorial()
    }

    private fun cargarHistorial() {
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        UserService.obtenerHistorialPedidos(usuarioId) { pedidos ->
            if (pedidos.isEmpty()) {
                tvVacio.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                tvVacio.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = HistorialAdapter(pedidos)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cargarHistorial()
    }
}