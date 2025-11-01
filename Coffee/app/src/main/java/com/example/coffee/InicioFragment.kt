package com.example.coffee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coffee.fragments.CafesFragment
import com.example.coffee.fragments.PlatillosFragment
import com.example.coffee.fragments.PostresFragment

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_inicio_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarBotones()
    }

    private fun configurarBotones() {
        view?.findViewById<View>(R.id.btnPostres)?.setOnClickListener {
            navegarAFragment(PostresFragment())
        }

        view?.findViewById<View>(R.id.btnCafes)?.setOnClickListener {
            navegarAFragment(CafesFragment())
        }

        view?.findViewById<View>(R.id.btnPlatillos)?.setOnClickListener {
            navegarAFragment(PlatillosFragment())
        }
    }

    private fun navegarAFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Para poder volver atrás
            .commit()
    }
}