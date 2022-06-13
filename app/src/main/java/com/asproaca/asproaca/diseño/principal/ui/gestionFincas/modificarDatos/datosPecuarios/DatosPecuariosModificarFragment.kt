package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.modificarDatos.datosPecuarios

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.DatosPecuariosModificarAdapter
import com.asproaca.asproaca.databinding.FragmentDatosPecuariosModificarBinding
import com.campo.campocolombiano.design.constantes.Constantes2

class DatosPecuariosModificarFragment : Fragment(R.layout.fragment_datos_pecuarios_modificar) {

private lateinit var binding: FragmentDatosPecuariosModificarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosPecuariosModificarBinding.bind(view)
        initRecyclerView()
    }

    private fun initRecyclerView() {

        binding.idRcyDatosPecuarios.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DatosPecuariosModificarAdapter(Constantes2.listaDatosFinca!!.datos_animal!!)
        }

    }

}