package com.example.tesistbc.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tesistbc.R
import com.google.firebase.auth.FirebaseAuth

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        auth = FirebaseAuth.getInstance()

        val txtRegistrarseAhora = findViewById<TextView>(R.id.txtRegistrarseAhora)
        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        txtRegistrarseAhora.setOnClickListener {
            val intent = Intent(this@InicioSesionActivity, registrarActivity::class.java)
            startActivity(intent)
        }

        btnIniciar.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@InicioSesionActivity, PrincipalActivity::class.java)
                            startActivity(intent)
                            finish() // Opcional: cierra la actividad de inicio de sesión
                        } else {
                            Toast.makeText(this, "Error al iniciar sesión: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor ingresa un email y una contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
