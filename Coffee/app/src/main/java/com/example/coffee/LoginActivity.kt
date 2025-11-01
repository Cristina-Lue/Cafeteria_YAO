package com.example.coffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        if (AuthService.usuarioEstaLogueado()) {
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        configurarEventos()
    }

    private fun configurarEventos() {
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegistrarse = findViewById<TextView>(R.id.txtRegistrarse)
        val txtOlvideContrasena = findViewById<TextView>(R.id.txtOlvideContrasena)

        btnLogin.setOnClickListener {
            iniciarSesion()
        }

        txtRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        txtOlvideContrasena.setOnClickListener {
            recuperarContrasena()
        }
    }

    private fun iniciarSesion() {
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)

        val email = etEmail.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (email.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Usar Firebase Authentication
        AuthService.iniciarSesion(this, email, contrasena)
    }

    private fun recuperarContrasena() {
        Toast.makeText(this, "Funcionalidad de recuperar contraseña próximamente", Toast.LENGTH_SHORT).show()
    }
}