package com.asproaca.asproaca.diseño.principal.ui.gestionFincas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.asproaca.asproaca.databinding.ActivityModificarFincaBinding
import com.campo.campocolombiano.design.constantes.Constantes2

class ModificarFincaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModificarFincaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityModificarFincaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}