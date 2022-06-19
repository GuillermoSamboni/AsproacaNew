package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.nuevaFincaEsatdo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asproaca.asproaca.databinding.ActivityNuevaFincaEstadoBinding

class NuevaFincaEstadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaFincaEstadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaFincaEstadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var hola = intent.extras?.get("Finca")
        binding.idGet.setText(hola.toString())
    }
}