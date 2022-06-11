package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosProductivos

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentProduccionBinding
import com.campo.campocolombiano.design.constantes.Constantes2

class ProduccionFragment : Fragment(R.layout.fragment_produccion) {
    private lateinit var binding: FragmentProduccionBinding

    private lateinit var fertilizantes_usados: String
    private lateinit var agroquimicos_usados: String
    private var equipo_proteccion: String? = null
    private lateinit var equipo_proteccion_estado: String
    private var cuenta_con_infraestructura: String? = null
    private lateinit var estado_infraestrcutura: String
    private lateinit var tipo_de_secado_de_cafe: String
    private lateinit var equipos_industriales: String
    private lateinit var numero_lavados: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProduccionBinding.bind(view)

        instanciDatosFormulario()

        binding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosProduccion()
        }

    }

    private fun pasarDatosProduccion() {
        instanciDatosFormulario()
        if (validarCamposFormulario()) {
            Constantes2.fertilizantes_usados = fertilizantes_usados
            Constantes2.agroquimicos_usados = agroquimicos_usados
            Constantes2.equipo_proteccion = equipo_proteccion
            Constantes2.equipo_proteccion_estado = equipo_proteccion_estado
            Constantes2.cuenta_con_infraestructura = cuenta_con_infraestructura
            Constantes2.estado_infraestrcutura = estado_infraestrcutura
            Constantes2.tipo_de_secado_de_cafe = tipo_de_secado_de_cafe
            Constantes2.equipos_industriales = equipos_industriales
            Constantes2.numero_lavados = numero_lavados

            //findNavController().navigate(R.id.action_produccionFragment_to_animalesFragment)
        }
    }

    private fun instanciDatosFormulario() {
        fertilizantes_usados = binding.idTxtFertilizantesUsados.text.toString()
        agroquimicos_usados = binding.idTxtAgroquimicosUsados.text.toString()

        val radioGroupProteccion = binding.idRadioGroupEquiposProteccion
        radioGroupProteccion.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEquiposProteccion -> {
                    equipo_proteccion = "Si"
                }
                R.id.idNoEquiposProteccion -> {
                    equipo_proteccion = "No"
                }
            }
        }

        val spinerEstadoProteccion = binding.idSpinerEstadoEquiposProteccion
        val itemsEstadoProteccion = arrayOf(
            "MALO",
            "REGULAR",
            "BUENO"
        )
        val arrayAdapterEstadoProteccion = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsEstadoProteccion
        )
        spinerEstadoProteccion.adapter = arrayAdapterEstadoProteccion
        equipo_proteccion_estado = spinerEstadoProteccion.selectedItem.toString()

        val radioGroupInfraestructura = binding.idRadioGroupEquiposInfraestructura
        radioGroupInfraestructura.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEquiposInfraestructura -> {
                    cuenta_con_infraestructura = "Si"
                }
                R.id.idNoEquiposInfraestructura -> {
                    cuenta_con_infraestructura = "No"
                }
            }
        }

        val spinerEstadoInfraestructura = binding.idSpinerEstadoEquiposInfraestructura
        val itemsEstadoInfraestructura = arrayOf(
            "MALO",
            "REGULAR",
            "BUENO"
        )
        val arrayAdapterEstadoInfraestructura = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsEstadoInfraestructura
        )
        spinerEstadoInfraestructura.adapter = arrayAdapterEstadoInfraestructura
        estado_infraestrcutura = spinerEstadoInfraestructura.selectedItem.toString()

        val spinerTipoSecadoCafe = binding.idSpinerTipoSecadocafe
        val itemsTipoSecadoCafe = arrayOf(
            "Malo",
            "Regular",
            "Bueno"
        )
        val arrayAdapterTipoSecadoCafe = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTipoSecadoCafe
        )
        spinerTipoSecadoCafe.adapter = arrayAdapterTipoSecadoCafe
        tipo_de_secado_de_cafe = spinerTipoSecadoCafe.selectedItem.toString()

        equipos_industriales = binding.idTxtEquiposIndustriales.text.toString()
        numero_lavados = binding.idTxtNumeroLavadosCafe.text.toString()
    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true
        if (TextUtils.isEmpty(fertilizantes_usados)) {
            esValido = false
            binding.idTxtFertilizantesUsados.error = "Campo requerido"
        } else binding.idTxtFertilizantesUsados.error = null

        if (TextUtils.isEmpty(agroquimicos_usados)) {
            esValido = false
            binding.idTxtAgroquimicosUsados.error = "Campo requerido"
        } else binding.idTxtAgroquimicosUsados.error = null

        if (TextUtils.isEmpty(equipos_industriales)) {
            esValido = false
            binding.idTxtEquiposIndustriales.error = "Campo requerido"
        } else binding.idTxtEquiposIndustriales.error = null

        if (TextUtils.isEmpty(numero_lavados)) {
            esValido = false
            binding.idTxtNumeroLavadosCafe.error = "Campo requerido"
        } else binding.idTxtNumeroLavadosCafe.error = null

        return esValido
    }

}