package com.example.tesistbc.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tesistbc.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class registrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Botón de registro
        val regButton: Button = findViewById(R.id.regbtnRegis)
        regButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtRegEmail).text.toString()
            val password = findViewById<EditText>(R.id.txtRegContra).text.toString()

            // Verificar si los campos no están vacíos
            if (email.isNotEmpty() && password.isNotEmpty()) {
                registrarUsuario(email, password)
            } else {
                Toast.makeText(baseContext, "Por favor ingresa un email y una contraseña", Toast.LENGTH_SHORT).show()
            }
        }
        // Botón de cancelar
        val cancelButton: Button = findViewById(R.id.regbtnCancel)
        cancelButton.setOnClickListener {
            if (isAnyFieldFilled()) {
                mostrarDialogoConfirmacion()
            } else {
                irAlInicioSesion()
            }
        }
    }

    private fun registrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Si es exitoso, guarda datos adicionales
                        guardarDatosAdicionales()
                    }
                } else {
                    Toast.makeText(baseContext, "Error en el registro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun guardarDatosAdicionales() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("usuarios")

        val userId = auth.currentUser?.uid
        val nombre = findViewById<EditText>(R.id.txtRegNombre).text.toString()
        val apellidoP = findViewById<EditText>(R.id.txtRegApelliP).text.toString()
        val apellidoM = findViewById<EditText>(R.id.txtRegApelliM).text.toString()
        val dni = findViewById<EditText>(R.id.txtRegDni).text.toString()
        val celular = findViewById<EditText>(R.id.txtRegCelular).text.toString()
        val sexo = findViewById<RadioGroup>(R.id.regRgrSexo).checkedRadioButtonId
        val sexoTexto = if (sexo == R.id.regRbtMaculino) "Masculino" else "Femenino"

        val userData = mapOf(
            "nombre" to nombre,
            "apellidoP" to apellidoP,
            "apellidoM" to apellidoM,
            "dni" to dni,
            "celular" to celular,
            "sexo" to sexoTexto
        )

        if (userId != null) {
            ref.child(userId).setValue(userData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        limpiarCampos()
                        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al guardar datos: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
    private fun limpiarCampos() {
        findViewById<EditText>(R.id.txtRegEmail).text.clear()
        findViewById<EditText>(R.id.txtRegContra).text.clear()
        findViewById<EditText>(R.id.txtRegNombre).text.clear()
        findViewById<EditText>(R.id.txtRegApelliP).text.clear()
        findViewById<EditText>(R.id.txtRegApelliM).text.clear()
        findViewById<EditText>(R.id.txtRegDni).text.clear()
        findViewById<EditText>(R.id.txtRegCelular).text.clear()
        findViewById<RadioGroup>(R.id.regRgrSexo).clearCheck()
    }
    // Método para verificar si algún campo está lleno
    private fun isAnyFieldFilled(): Boolean {
        val email = findViewById<EditText>(R.id.txtRegEmail).text.toString()
        val password = findViewById<EditText>(R.id.txtRegContra).text.toString()
        val nombre = findViewById<EditText>(R.id.txtRegNombre).text.toString()
        val apellidoP = findViewById<EditText>(R.id.txtRegApelliP).text.toString()
        val apellidoM = findViewById<EditText>(R.id.txtRegApelliM).text.toString()
        val dni = findViewById<EditText>(R.id.txtRegDni).text.toString()
        val celular = findViewById<EditText>(R.id.txtRegCelular).text.toString()

        return email.isNotEmpty() || password.isNotEmpty() || nombre.isNotEmpty() || apellidoP.isNotEmpty() || apellidoM.isNotEmpty() || dni.isNotEmpty() || celular.isNotEmpty()
    }
    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Estás seguro de que deseas cancelar? Se perderán los datos ingresados.")
            .setPositiveButton("Sí") { _, _ ->
                irAlInicioSesion()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun irAlInicioSesion() {
        startActivity(Intent(this, InicioSesionActivity::class.java))
        finish()
    }
}
