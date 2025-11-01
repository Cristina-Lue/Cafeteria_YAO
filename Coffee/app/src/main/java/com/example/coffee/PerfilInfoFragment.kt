package com.example.coffee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class PerfilInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_perfil_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        val usuario = FirebaseAuth.getInstance().currentUser

        tvEmail.text = usuario?.email ?: "No disponible"
        tvNombre.text = usuario?.displayName ?: "Usuario"

        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}