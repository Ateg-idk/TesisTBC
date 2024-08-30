package com.example.tesistbc.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tesistbc.R

class InicioSesionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)


        val txtRegistrarseAhora = findViewById<TextView>(R.id.txtRegistrarseAhora)


        txtRegistrarseAhora.setOnClickListener {
            val intent = Intent(this@InicioSesionActivity, registrarActivity::class.java)
            startActivity(intent)
        }
    }
}