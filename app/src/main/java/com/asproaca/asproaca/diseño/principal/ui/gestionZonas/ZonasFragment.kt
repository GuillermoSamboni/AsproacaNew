package com.asproaca.asproaca.diseño.principal.ui.gestionZonas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentZonasBinding

class ZonasFragment : Fragment(R.layout.fragment_zonas) {
    private lateinit var binding: FragmentZonasBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentZonasBinding.bind(view)

    }
}