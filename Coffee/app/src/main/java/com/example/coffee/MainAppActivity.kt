package com.example.coffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.coffee.fragments.CarritoFragment

class MainAppActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)


        if (!AuthService.usuarioEstaLogueado()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)

        configurarNavegacion()
        cargarFragmentoInicio()
    }

    private fun configurarNavegacion() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    cargarFragmento(InicioFragment())
                    true
                }
                R.id.nav_favoritos -> {
                    cargarFragmento(FavoritosFragment())
                    true
                }
                R.id.nav_carrito -> {
                    cargarFragmento(CarritoFragment())
                    true
                }
                R.id.nav_perfil -> {
                    cargarFragmento(PerfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarFragmentoInicio() {
        cargarFragmento(InicioFragment())
    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}