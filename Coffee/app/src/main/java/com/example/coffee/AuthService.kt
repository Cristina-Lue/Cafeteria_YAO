package com.example.coffee



import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

object AuthService {

    private val auth = FirebaseAuth.getInstance()

    fun registrarUsuario(
        activity: AppCompatActivity,
        email: String,
        contrasena: String
    ) {
        auth.createUserWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    navegarAMainApp(activity)
                } else {
                    Toast.makeText(
                        activity,
                        "Error al registrar: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun iniciarSesion(activity: AppCompatActivity, email: String, contrasena: String) {
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    navegarAMainApp(activity)
                } else {
                    Toast.makeText(
                        activity,
                        "Error al iniciar sesión: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun usuarioEstaLogueado(): Boolean {
        return auth.currentUser != null
    }

    private fun navegarAMainApp(activity: AppCompatActivity) {
        val intent = Intent(activity, MainAppActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}