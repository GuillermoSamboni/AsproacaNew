package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosCasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentCocinaBinding
import com.campo.campocolombiano.design.constantes.Constantes2


class CocinaFragment : Fragment(R.layout.fragment_cocina) {
    private lateinit var binding: FragmentCocinaBinding

    private lateinit var tipo_estufa: String
    private lateinit var tipo_combustible_alimentos: String
    private lateinit var tipo_combustible_industriales: String
    private lateinit var fuente_agua_consumo_domestico: String
    private lateinit var fuente_agua_consumo_industrial: String
    private var tratamiento_agua_ducha: String? = ""
    private var tratamiento_agua_lavadero: String? = ""
    private var tratamiento_agua_lavaplatos: String? = ""
    private var tratamiento_agua_residual: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCocinaBinding.bind(view)
        instanciaDatosFormulario()

        binding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosCocina()
        }
    }

    private fun instanciaDatosFormulario() {
        val spinerTipoEstufa = binding.idSpinerTipoEstufa
        val itemsTiposEstufa = arrayOf(
            "ESTUFA ELECTRICA",
            "ESTUFAESTUFA De PALLETES",
            "ESTUFA DE PARAFINA",
            "ESTUFA DE BUTANO",
            "ESTUFA DE ACEITE",
            "ESTUFA DE LEÑA",
            "ESTUFA DE BIOMASA",
            "ESTUFA DE ALOJENO",
            "OTRO"
        )
        val arrayAdapterEstufa = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposEstufa
        )
        spinerTipoEstufa.adapter = arrayAdapterEstufa
        tipo_estufa = spinerTipoEstufa.selectedItem.toString()

        val spinerTipoCombustibleAlimentos = binding.idSpinerActividadesDomesticas
        val itemsTiposCombustibleAlimentos = arrayOf(
            "LEÑA",
            "GAS",
            "GASOLINA",
            "ELECTRICIDAD",
            "OTRO"
        )
        val arrayAdapterCombustibleAlimentos = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposCombustibleAlimentos
        )
        spinerTipoCombustibleAlimentos.adapter = arrayAdapterCombustibleAlimentos
        tipo_combustible_alimentos = spinerTipoCombustibleAlimentos.selectedItem.toString()

        val spinerTipoCombustibleIndustrial = binding.idSpinerActividadesIndustriales
        val itemsTiposCombustibleIndustrial = arrayOf(
            "LEÑA",
            "GAS",
            "GASOLINA",
            "ELECTRICIDAD",
            "OTRO"
        )
        val arrayAdapterCombustibleIndustrial = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposCombustibleIndustrial
        )
        spinerTipoCombustibleIndustrial.adapter = arrayAdapterCombustibleIndustrial
        tipo_combustible_industriales = spinerTipoCombustibleIndustrial.selectedItem.toString()

        val spinerTipoConsumoDomestico = binding.idSpinerConsumoDomestico
        val itemsTiposConsumoDomestico = arrayOf(
            "ACUEDUCTO",
            "LLUVIA",
            "RIO",
            "ALGIBE",
            "OTRO"
        )
        val arrayAdapterConsumoDomestico = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposConsumoDomestico
        )
        spinerTipoConsumoDomestico.adapter = arrayAdapterConsumoDomestico
        fuente_agua_consumo_domestico = spinerTipoConsumoDomestico.selectedItem.toString()

        val spinerTipoConsumoIndustrial = binding.idSpinerConsumoIndustrial
        val itemsTiposConsumoIndustrial = arrayOf(
            "ACUEDUCTO",
            "LLUVIA",
            "RIO",
            "ALGIBE",
            "OTRO"
        )
        val arrayAdapterConsumoIndustrial = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposConsumoIndustrial
        )
        spinerTipoConsumoIndustrial.adapter = arrayAdapterConsumoIndustrial
        fuente_agua_consumo_industrial = spinerTipoConsumoIndustrial.selectedItem.toString()

        val radioGroupAguaDucha = binding.idRadioGroupTratamientoDucha
        radioGroupAguaDucha.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTratamientoDucha -> {
                    tratamiento_agua_ducha = "Si"
                }
                R.id.idNoTratamientoDucha -> {
                    tratamiento_agua_ducha = "No"
                }
            }
        }

        val radioGroupAguaLavadero = binding.idRadioGroupTratamientoLavadero
        radioGroupAguaLavadero.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTratamientoLavadero -> {
                    tratamiento_agua_lavadero = "Si"
                }
                R.id.idNoTratamientoLavadero -> {
                    tratamiento_agua_lavadero = "No"
                }
            }
        }

        val radioGroupAguaLavaPlatos = binding.idRadioGroupTratamientoLavaPlatos
        radioGroupAguaLavaPlatos.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTratamientoLavaPlatos -> {
                    tratamiento_agua_lavaplatos = "Si"
                }
                R.id.idNoTratamientoLavaPlatos -> {
                    tratamiento_agua_lavaplatos = "No"
                }
            }
        }

        val radioGroupAguaResidual = binding.idRadioGroupTratamientoAguaResidual
        radioGroupAguaResidual.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTratamientoAguaResidual -> {
                    tratamiento_agua_residual = "Si"
                }
                R.id.idNoTratamientoAguaResidual -> {
                    tratamiento_agua_residual = "No"
                }
            }
        }
    }

    private fun pasarDatosCocina() {
        instanciaDatosFormulario()
        if (validarCamposFormulario()) {
            Constantes2.tipo_estufa = tipo_estufa
            Constantes2.tipo_combustible_alimentos = tipo_combustible_alimentos
            Constantes2.tipo_combustible_industriales = tipo_combustible_industriales
            Constantes2.fuente_comsumo_domestico = fuente_agua_consumo_domestico
            Constantes2.fuente_comsumo_industrial = fuente_agua_consumo_industrial
            Constantes2.tratamiento_agua_ducha = tratamiento_agua_ducha
            Constantes2.tratamiento_lavadero = tratamiento_agua_lavadero
            Constantes2.tratamiento_lavaplatos = tratamiento_agua_lavaplatos
            Constantes2.tratamiento_agua_residual =
                tratamiento_agua_residual
            findNavController().navigate(R.id.action_cocinaFragment_to_datosSocialesFragment2)
        }
    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true
        val esValido2 = true

        if (!(binding.idSiTratamientoDucha.isChecked || binding.idNoTratamientoDucha.isChecked)) {
            esValido = false
            binding.idErrorAguaDucha.visibility = View.VISIBLE
        } else {
            binding.idErrorAguaDucha.visibility = View.GONE
        }

        if (!(binding.idSiTratamientoLavadero.isChecked || binding.idNoTratamientoLavadero.isChecked)) {
            esValido = false
            binding.idErrorAguaLavadero.visibility = View.VISIBLE
        } else {
            binding.idErrorAguaLavadero.visibility = View.GONE
        }

        if (!(binding.idSiTratamientoLavaPlatos.isChecked || binding.idNoTratamientoLavaPlatos.isChecked)) {
            esValido = false
            binding.idErrorAguaLavaplatos.visibility = View.VISIBLE
        } else {
            binding.idErrorAguaLavaplatos.visibility = View.GONE
        }

        if (!(binding.idSiTratamientoAguaResidual.isChecked || binding.idNoTratamientoAguaResidual.isChecked)) {
            esValido = false
            binding.idErrorAguaResidual.visibility = View.VISIBLE
        } else {
            binding.idErrorAguaResidual.visibility = View.GONE
        }

        return esValido
    }
}