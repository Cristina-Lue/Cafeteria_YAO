package com.example.coffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        configurarEventos()
    }

    private fun configurarEventos() {
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val txtIniciarSesion = findViewById<TextView>(R.id.txtIniciarSesion)

        btnRegistrarse.setOnClickListener {
            registrarUsuario()
        }

        txtIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registrarUsuario() {
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmarContrasena)

        val email = etEmail.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()
        val confirmarContrasena = etConfirmarContrasena.text.toString().trim()

        if (email.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (contrasena != confirmarContrasena) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        if (contrasena.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }


        AuthService.registrarUsuario(this, email, contrasena)
    }
}