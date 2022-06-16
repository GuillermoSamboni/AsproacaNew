package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosCasa

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentCasaBinding
import com.campo.campocolombiano.design.constantes.Constantes2


class CasaFragment : Fragment(R.layout.fragment_casa) {
    private lateinit var binding: FragmentCasaBinding

    private var servicio_acueducto: String? = ""
    private var servicio_alcantarillado: String? = ""
    private var servicio_electrico: String? = ""
    private var servicio_internet: String? = ""
    private lateinit var tipo_techo: String
    private lateinit var tipo_pared: String
    private lateinit var numero_banios: String
    private lateinit var tipo_piso: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCasaBinding.bind(view)
        instanciaDatosFormulario()

        binding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosFormulario()
        }
    }

    private fun pasarDatosFormulario() {
        instanciaDatosFormulario()
        if (validarCamposFormulario()) {
            Constantes2.servicio_acueducto = servicio_acueducto
            Constantes2.servicio_alcantarillado = servicio_alcantarillado
            Constantes2.servicio_electrico = servicio_electrico
            Constantes2.servicio_internet = servicio_internet
            Constantes2.tipo_techo = tipo_techo
            Constantes2.tipo_pared = tipo_pared
            Constantes2.numero_banios = numero_banios
            Constantes2.tipo_piso = tipo_piso

            findNavController().navigate(R.id.action_casaFragment_to_cocinaFragment)
        }
    }

    private fun instanciaDatosFormulario() {
        val radioGroupAcueducto = binding.idRadioGroupAcueducto
        radioGroupAcueducto.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiAcueducto -> {
                    servicio_acueducto = "Si"
                }
                R.id.idNoAcueducto -> {
                    servicio_acueducto = "No"
                }
            }
        }

        val radioGroupAlcantarillado = binding.idRadioGroupAlcantarillado
        radioGroupAlcantarillado.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiAlcantarillado -> {
                    servicio_alcantarillado = "Si"
                }
                R.id.idNoAlcantarillado -> {
                    servicio_alcantarillado = "No"
                }
            }
        }

        val radioGroupElectrico = binding.idRadioGroupElectrico
        radioGroupElectrico.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiElectrico -> {
                    servicio_electrico = "Si"
                }
                R.id.idNoElectrico -> {
                    servicio_electrico = "No"
                }
            }
        }

        val radioGroupInternet = binding.idRadioGroupInternet
        radioGroupInternet.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiInternet -> {
                    servicio_internet = "Si"
                }
                R.id.idNoInternet -> {
                    servicio_internet = "No"
                }
            }
        }

        val spinerTipoTecho = binding.idSpinerTipoTecho
        val itemsTiposTecho = arrayOf(
            "METALICO", "MADERA", "CONCRETO", "TEJA", "OTRO"
        )
        val arrayAdapterTipoTecho = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposTecho
        )
        spinerTipoTecho.adapter = arrayAdapterTipoTecho
        tipo_techo = spinerTipoTecho.selectedItem.toString()


        val spinerTipoPared = binding.idSpinerTipoPared
        val itemsTiposPared = arrayOf(
            "MADERA", "METALICO", "PREFABRICADO", "MATERIAL", "BAREHEQUE", "OTRO"
        )
        val arrayAdapterTipoPared = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposPared
        )
        spinerTipoPared.adapter = arrayAdapterTipoPared
        tipo_pared = spinerTipoPared.selectedItem.toString()

        numero_banios = binding.idTxtNumeroBanios.text.toString()

        val spinerTipoPiso = binding.idSpinerTipoPiso
        val itemsTiposPiso = arrayOf(
            "MADERA", "CEMENTO", "ADOQUIN", "EN TIERRA", "BALDOSA", "OTRO"
        )
        val arrayAdapterTipoPiso = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTiposPiso
        )
        spinerTipoPiso.adapter = arrayAdapterTipoPiso
        tipo_piso = spinerTipoPiso.selectedItem.toString()

    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true

        if (!(binding.idSiAcueducto.isChecked || binding.idNoAcueducto.isChecked)) {
            esValido = false
            binding.idErrorAcueducto.visibility = View.VISIBLE
        } else {
            binding.idErrorAcueducto.visibility = View.GONE
        }

        if (!(binding.idSiAlcantarillado.isChecked || binding.idNoAlcantarillado.isChecked)) {
            esValido = false
            binding.idErrorAlcantarillado.visibility = View.VISIBLE
        } else {
            binding.idErrorAlcantarillado.visibility = View.GONE
        }

        if (!(binding.idSiElectrico.isChecked || binding.idNoElectrico.isChecked)) {
            esValido = false
            binding.idErrorElectrico.visibility = View.VISIBLE
        } else {
            binding.idErrorElectrico.visibility = View.GONE
        }

        if (!(binding.idSiInternet.isChecked || binding.idNoInternet.isChecked)) {
            esValido = false
            binding.idErrorInternet.visibility = View.VISIBLE
        } else {
            binding.idErrorInternet.visibility = View.GONE
        }

        if (TextUtils.isEmpty(numero_banios)) {
            esValido = false
            binding.idTxtNumeroBanios.error = "Campo requerido"
        } else binding.idTxtNumeroBanios.error = null

        return esValido
    }
}