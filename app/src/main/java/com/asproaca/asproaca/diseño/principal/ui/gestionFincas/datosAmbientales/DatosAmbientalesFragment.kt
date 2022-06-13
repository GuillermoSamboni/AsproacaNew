package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosAmbientales

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentAnimalesBinding
import com.asproaca.asproaca.databinding.FragmentDatosAmbientalesBinding
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class DatosAmbientalesFragment : Fragment(R.layout.fragment_datos_ambientales) {
    private lateinit var binding: FragmentDatosAmbientalesBinding
    private lateinit var viewModel: DatosAmbintalesViewModel

    private var tiene_ecosistema_acuaticos_naturales: String? = null
    private lateinit var ecosistemas_naturales_acuaticos: String
    private var tiene_ecosistema_acuaticos_artificiales: String? = null
    private lateinit var ecosistemas_artificial_acuaticos: String
    private var tiene_ecositemas_terrestes_naturales: String? = null
    private lateinit var ecosistemas_terrestre_natural: String

    private lateinit var area_ecosistemas_terrestre_natural: String
    private lateinit var consesion_agua: String
    private lateinit var tipo_arboles_descripcion: String
    private var tratamiento_basura: String? = null
    private var separacion_basura: String? = null
    private var cobertura_suelos: String? = null
    private var areas_con_erosion: String? = null
    private var areas_con_evidencias_remocion: String? = null
    private var animales_silvestres_en_cautivero: String? = null
    private var captacion_agua_lluvia: String? = null
    private var preferencias: Preferencias? = null

    private var dataBase = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosAmbientalesBinding.bind(view)

        viewModel = ViewModelProvider(this)[DatosAmbintalesViewModel::class.java]
        dataBase.firestoreSettings.isPersistenceEnabled
        dataBase.firestoreSettings.cacheSizeBytes

        instanciaDatosFormulario()

        binding.idBtnFinalizaregistro.setOnClickListener {
            registrarFinca()
        }
    }

    private fun registrarFinca() {
        instanciaDatosFormulario()
        if (validarCamposFormulario()) {
            //Constantes2.idFinca = UUID.randomUUID().toString()
            viewModel.clickRegistroFinca()
            viewModel.resultRegister.observe(this@DatosAmbientalesFragment.requireActivity(),
                Observer { success ->
                    if (success == true) {
                        findNavController().navigate(R.id.action_datosAmbientalesFragment_to_nav_fincas)
                    } else {
                        //
                    }
                })
        }

    }

    private fun instanciaDatosFormulario() {
        val radioGroupAcuaNaturales = binding.idRadioGroupEcosistemasAcuaNaturales
        radioGroupAcuaNaturales.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEcosistemasNaturales -> {
                    tiene_ecosistema_acuaticos_naturales = "Si"
                }
                R.id.idNoEcosistemasNaturales -> {
                    tiene_ecosistema_acuaticos_naturales = "No"
                }
            }
        }

        val spinerNatuAcuaticos = binding.idSpinerEcosistemaNatural
        val itemsNatuAcuaticos = arrayOf(
            "NACIMIENTOS",
            "AGUA SUBTERRÁNEAS",
            "QUEBRADAS", "RIOS"
        )
        val arrayAdapterNatuAcuaticos = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsNatuAcuaticos
        )
        spinerNatuAcuaticos.adapter = arrayAdapterNatuAcuaticos
        ecosistemas_naturales_acuaticos = spinerNatuAcuaticos.selectedItem.toString()

        val radioGroupAcuaArtificiales = binding.idRadioGroupEcosistemasArtifiales
        radioGroupAcuaArtificiales.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEcosistemasArtifiales -> {
                    tiene_ecosistema_acuaticos_artificiales = "Si"
                }
                R.id.idNoEcosistemasArtifiales -> {
                    tiene_ecosistema_acuaticos_artificiales = "No"
                }
            }
        }

        val spinerAcuaArtificiales = binding.idSpinerEcosistemaArtificial
        val itemsAcuaArtificiales = arrayOf(
            "POZO",
            "PECES", "RESERVORIO DE AGUA", "OTRO"
        )
        val arrayAdapterAcuaArtificiales = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsAcuaArtificiales
        )
        spinerAcuaArtificiales.adapter = arrayAdapterAcuaArtificiales
        ecosistemas_artificial_acuaticos = spinerAcuaArtificiales.selectedItem.toString()

        val radioGroupTerrestreNaturales = binding.idRadioGroupTerrestreNatural
        radioGroupTerrestreNaturales.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTerrestreNatural -> {
                    tiene_ecositemas_terrestes_naturales = "Si"
                }
                R.id.idNoTerrestreNatural -> {
                    tiene_ecositemas_terrestes_naturales = "No"
                }
            }
        }

        val spinerTerrestreNaturales = binding.idSpinerEcosistemaTerrestreNatural
        val itemsTerrestreNaturales = arrayOf("BOSQUES", "GUADULAES", "RESERVAS")
        val arrayAdapterTerrestreNaturales = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTerrestreNaturales
        )
        spinerTerrestreNaturales.adapter = arrayAdapterTerrestreNaturales
        ecosistemas_terrestre_natural = spinerTerrestreNaturales.selectedItem.toString()


        area_ecosistemas_terrestre_natural =
            binding.idTxtAreaEcosistemaTerrestreaNATURAL.text.toString()
        consesion_agua = binding.idTxtConsesionAgua.text.toString()
        tipo_arboles_descripcion = binding.idTxtTipoArboles.text.toString()

        val radioGroupTratamientoBasura = binding.idRadioGroupTerrestreNatural
        radioGroupTratamientoBasura.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTratamientoBasura -> {
                    tratamiento_basura = "Si"
                }
                R.id.idNoTratamientoBasura -> {
                    tratamiento_basura = "No"
                }
            }
        }

        val radioGroupSeparaBasura = binding.idRadioGroupSeparacionBasura
        radioGroupSeparaBasura.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiSeparaBasura -> {
                    separacion_basura = "Si"
                }
                R.id.idNoSeparaBasura -> {
                    separacion_basura = "No"
                }
            }
        }

        val radioGroupCoberturaSuelos = binding.idRadioGroupCoverturaSuelos
        radioGroupCoberturaSuelos.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiCoverturaSuelos -> {
                    cobertura_suelos = "Si"
                }
                R.id.idNoCoverturaSuelos -> {
                    cobertura_suelos = "No"
                }
            }
        }

        val radioGroupAreasConErosion = binding.idRadioGroupAreasErocion
        radioGroupAreasConErosion.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiAreasErocion -> {
                    areas_con_erosion = "Si"
                }
                R.id.idNoAreasErocion -> {
                    areas_con_erosion = "No"
                }
            }
        }

        val radioGroupAreasConRemocion = binding.idRadioGroupAreasRemcion
        radioGroupAreasConRemocion.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiAreasRemcion -> {
                    areas_con_evidencias_remocion = "Si"
                }
                R.id.idNoAreasRemcion -> {
                    areas_con_evidencias_remocion = "No"
                }
            }
        }

        val radioGroupAnimalCautiverio = binding.idRadioGroupAnimalesEnCautiverio
        radioGroupAnimalCautiverio.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiAnimalesEnCautiverio -> {
                    animales_silvestres_en_cautivero = "Si"
                }
                R.id.idNoAnimalesEnCautiverio -> {
                    animales_silvestres_en_cautivero = "No"
                }
            }
        }

        val radioGroupAguaLluvia = binding.idRadioGroupCaptacionAguaLluvia
        radioGroupAguaLluvia.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiCaptacionAguaLluvia -> {
                    captacion_agua_lluvia = "Si"
                }
                R.id.idNoCaptacionAguaLluvia -> {
                    captacion_agua_lluvia = "No"
                }
            }
        }


        Constantes2.tiene_ecosistema_acuaticos_naturales = tiene_ecosistema_acuaticos_naturales
        Constantes2.ecosistemas_naturales_acuaticos = ecosistemas_naturales_acuaticos
        Constantes2.tiene_ecosistema_acuaticos_artificiales =
            tiene_ecosistema_acuaticos_artificiales
        Constantes2.ecosistemas_artificial_acuaticos = ecosistemas_artificial_acuaticos
        Constantes2.tiene_ecositemas_terrestes_naturales = tiene_ecositemas_terrestes_naturales
        Constantes2.ecosistemas_terrestre_natural = ecosistemas_terrestre_natural
        Constantes2.area_ecosistemas_terrestre_natural = area_ecosistemas_terrestre_natural
        Constantes2.consesion_agua = consesion_agua
        Constantes2.tipo_arboles_descripcion = tipo_arboles_descripcion
        Constantes2.tratamiento_basura = tratamiento_basura
        Constantes2.separacion_basura = separacion_basura
        Constantes2.cobertura_suelos = cobertura_suelos
        Constantes2.areas_con_erosion = areas_con_erosion
        Constantes2.areas_con_evidencias_remocion = areas_con_evidencias_remocion
        Constantes2.animales_silvestres_en_cautivero = animales_silvestres_en_cautivero
        Constantes2.captacion_agua_lluvia = captacion_agua_lluvia
        preferencias = Preferencias(requireContext())
        Constantes2.idUsuario = preferencias!!.obtenerIdUsuario()
        Constantes2.encargadoRegistro = preferencias!!.obtenerRol()
    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true

        if (!(binding.idSiEcosistemasNaturales.isChecked || binding.idNoEcosistemasNaturales.isChecked)) {
            esValido = false
            binding.idErrorAcuNatu.visibility = View.VISIBLE
        } else {
            binding.idErrorAcuNatu.visibility = View.GONE
        }

        if (!(binding.idSiEcosistemasArtifiales.isChecked || binding.idNoEcosistemasArtifiales.isChecked)) {
            esValido = false
            binding.idErrorAcuArtifi.visibility = View.VISIBLE
        } else {
            binding.idErrorAcuArtifi.visibility = View.GONE
        }

        if (!(binding.idSiTerrestreNatural.isChecked || binding.idNoTerrestreNatural.isChecked)) {
            esValido = false
            binding.idErrorTerresNatu.visibility = View.VISIBLE
        } else {
            binding.idErrorTerresNatu.visibility = View.GONE
        }

        if (!(binding.idSiTratamientoBasura.isChecked || binding.idNoTratamientoBasura.isChecked)) {
            esValido = false
            binding.idErrorTrataBasura.visibility = View.VISIBLE
        } else {
            binding.idErrorTrataBasura.visibility = View.GONE
        }

        if (!(binding.idSiSeparaBasura.isChecked || binding.idNoSeparaBasura.isChecked)) {
            esValido = false
            binding.idErrorSeparaBasu.visibility = View.VISIBLE
        } else {
            binding.idErrorSeparaBasu.visibility = View.GONE
        }

        if (!(binding.idSiCoverturaSuelos.isChecked || binding.idNoCoverturaSuelos.isChecked)) {
            esValido = false
            binding.idErrorCoverSuelos.visibility = View.VISIBLE
        } else {
            binding.idErrorCoverSuelos.visibility = View.GONE
        }

        if (!(binding.idSiAreasErocion.isChecked || binding.idNoAreasErocion.isChecked)) {
            esValido = false
            binding.idErrorAreasErocion.visibility = View.VISIBLE
        } else {
            binding.idErrorAreasErocion.visibility = View.GONE
        }

        if (!(binding.idSiAreasRemcion.isChecked || binding.idSiAreasRemcion.isChecked)) {
            esValido = false
            binding.idErrorEvidenciaRemocion.visibility = View.VISIBLE
        } else {
            binding.idErrorEvidenciaRemocion.visibility = View.GONE
        }

        if (!(binding.idSiAnimalesEnCautiverio.isChecked || binding.idNoAnimalesEnCautiverio.isChecked)) {
            esValido = false
            binding.idErrorCapturaAnimales.visibility = View.VISIBLE
        } else {
            binding.idErrorCapturaAnimales.visibility = View.GONE
        }

        if (!(binding.idSiCaptacionAguaLluvia.isChecked || binding.idNoCaptacionAguaLluvia.isChecked)) {
            esValido = false
            binding.idErrorCapturaAguaLluvia.visibility = View.VISIBLE
        } else {
            binding.idErrorCapturaAguaLluvia.visibility = View.GONE
        }




        if (TextUtils.isEmpty(area_ecosistemas_terrestre_natural)) {
            esValido = false
            binding.idTxtAreaEcosistemaTerrestreaNATURAL.error = "Campo requerido"
        } else binding.idTxtAreaEcosistemaTerrestreaNATURAL.error = null

        if (TextUtils.isEmpty(consesion_agua)) {
            esValido = false
            binding.idTxtConsesionAgua.error = "Campo requerido"
        } else binding.idTxtConsesionAgua.error = null

        if (TextUtils.isEmpty(tipo_arboles_descripcion)) {
            esValido = false
            binding.idTxtTipoArboles.error = "Campo requerido"
        } else binding.idTxtTipoArboles.error = null
        return esValido
    }


}