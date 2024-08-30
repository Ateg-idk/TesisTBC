package com.example.tesistbc.actividades

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tesistbc.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Configurar el BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Establecer el listener para los ítems del menú
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_hoy -> {
                    // Acción para "Hoy"
                    startActivity(Intent(this, HoyActivity::class.java))
                    true
                }
                R.id.nav_consejos -> {
                    // Acción para "Consejos"
                    startActivity(Intent(this, ConsejosActivity::class.java))
                    true
                }
                R.id.nav_mensajes -> {
                    // Acción para "Mensajes"
                    startActivity(Intent(this, MensajesActivity::class.java))
                    true
                }
                R.id.nav_perfil -> {
                    // Acción para "Perfil"
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
