package com.example.tesistbc.actividades

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tesistbc.R

class CargaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)
        startAnimation()
    }

    private fun startAnimation() {
        Handler().postDelayed({
            startActivity(Intent(this@CargaActivity, InicioSesionActivity::class.java))
            finish()
        }, 2500)
    }
}
