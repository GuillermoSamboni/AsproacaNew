package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosSociales

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertaIntegrantesBinding
import com.asproaca.asproaca.databinding.FragmentDatosSocialesBinding
import com.asproaca.asproaca.modelos.DatosSociales
import com.asproaca.asproaca.modelos.IntegrantesFamilia
import com.campo.campocolombiano.design.constantes.Constantes2


class DatosSocialesFragment : Fragment(R.layout.fragment_datos_sociales) {
    private lateinit var binding: FragmentDatosSocialesBinding

    private var nombreCompleto: String? = null
    private var identificacion: String? = null
    private var fechaNacimiento: String? = null
    private var telefono: String? = null
    private var correoElectronico: String? = null
    private var edad: String? = null
    private var nivelManejoDispositivos: String? = null
    private var tipoPoblacion: String? = null
    private var genero: String? = null
    private var nivelAcademico: String? = null
    private var numeroDeIntegrantes = "0"
    private var listIntegrantes = mutableListOf<IntegrantesFamilia>()
    var contIntegrantes = 0

    private var edadA: String? = null
    private var generoA: String? = null
    private var nivelAcademicoA: String? = null
    private var tieneDiscapacidad: String? = null
    private var discapacidad: String? = null
    private var esValidoAlerta: Boolean? = true

    private lateinit var alerta_integrante: AlertDialog
    private lateinit var dialogBinding: AlertaIntegrantesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosSocialesBinding.bind(view)
        referenciaDatosFormulario()

        binding.idBtnContinuarProceso.setOnClickListener {
            if (validarFormulario()) {
                numeroIntegrantes()
            }
        }
    }

    private fun referenciaDatosFormulario() {
        nombreCompleto = binding.idTxtNombreCompleto.text.toString()
        identificacion = binding.idTxtNumeroIdentificacion.text.toString()

        binding.idBtnFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }
        telefono = binding.idTxtTelefono.text.toString()
        correoElectronico = binding.idTxtCorreo.text.toString()

        val spinerNivelManejoDispositivos = binding.idSpinerNivelManejoDispositivos
        val itemManejoDispositivos = arrayOf(
            "ALTO", "MEDIO", "BAJO"
        )
        val arrayAdapterManejoDispositivos = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemManejoDispositivos
        )
        spinerNivelManejoDispositivos.adapter = arrayAdapterManejoDispositivos
        nivelManejoDispositivos = spinerNivelManejoDispositivos.selectedItem.toString()


        val spinerTipoPoblacion = binding.idSpinerTipoPoblacion
        val itemTipoPoblacion = arrayOf(
            "INDIGENA", "AFRO", "PALENQUERO", "CAMPESINO", "NINGUNO"
        )
        val arrayAdapterTipoPoblacion = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemTipoPoblacion
        )
        spinerTipoPoblacion.adapter = arrayAdapterTipoPoblacion
        tipoPoblacion = spinerTipoPoblacion.selectedItem.toString()
        numeroDeIntegrantes = binding.idTxtNumeroIntegrantes.text.toString()

    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + "/" + (month + 1) + "/" + year
                fechaNacimiento = selectedDate
                binding.idTxtFechaNacimiento.text = fechaNacimiento
            })

        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun validarFormulario(): Boolean {
        referenciaDatosFormulario()
        var esValido = true

        if (TextUtils.isEmpty(nombreCompleto)) {
            binding.idTxtNombreCompleto.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtNombreCompleto.error = null
            esValido = true
        }

        if (TextUtils.isEmpty(identificacion)) {
            binding.idTxtNumeroIdentificacion.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtNumeroIdentificacion.error = null
            esValido = true
        }

        if (fechaNacimiento == "dd/mm/yyyy") {
            binding.idErrorFechaNac.visibility = View.VISIBLE
            esValido = false
        } else {
            binding.idErrorFechaNac.visibility = View.GONE
            esValido = true
        }

        if (TextUtils.isEmpty(telefono)) {
            binding.idTxtTelefono.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtTelefono.error = null
            esValido = true
        }

        if (TextUtils.isEmpty(numeroDeIntegrantes)) {
            binding.idTxtNumeroIntegrantes.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtNumeroIntegrantes.error = null
            esValido = true
        }
        return esValido
    }

    private fun numeroIntegrantes() {
        if (numeroDeIntegrantes!!.toInt() > 0) {
            mostrarAlertaIntegrantes()
        } else {
            pasarDatosFormulario()
            findNavController().navigate(R.id.action_datosSocialesFragment_to_datosProductivosFragment2)
        }
    }

    private fun pasarDatosFormulario() {
        referenciaDatosFormulario()
        if (validarFormulario()) {
            if (numeroDeIntegrantes == "0" || numeroDeIntegrantes == "") {
                Constantes2.nombre = nombreCompleto
                Constantes2.identificacion = identificacion
                Constantes2.fechaNacimiento = fechaNacimiento
                Constantes2.telefono = telefono
                Constantes2.correoElectronico = correoElectronico
                Constantes2.edad = edad
                Constantes2.tipoPoblacion = tipoPoblacion
                Constantes2.nivelAcademico = nivelAcademico
                Constantes2.numeroIntegrantes = numeroDeIntegrantes
                Constantes2.nivelManejoDispositivos = nivelManejoDispositivos
                Constantes2.listaIntegrantes = listIntegrantes
            } else if (numeroDeIntegrantes != "0") {
                mostrarAlertaIntegrantes()
            }
        }
    }

    private fun mostrarAlertaIntegrantes() {
        dialogBinding = AlertaIntegrantesBinding.inflate(LayoutInflater.from(context))
        alerta_integrante = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        instanciaFormularioAlerta()

        dialogBinding.idConteoAlerta.setText(numeroDeIntegrantes)
        dialogBinding.idBtnAgregarIntegranteAlerta.setOnClickListener {
            if (validarFormularioAlerta()) {
                val numeroIntegrantes = IntegrantesFamilia(
                    edadA,
                    generoA,
                    nivelAcademicoA,
                    tieneDiscapacidad,
                    discapacidad
                )
                listIntegrantes.addAll(listOf(numeroIntegrantes))
                Constantes2.listaIntegrantes = listIntegrantes
                dialogBinding.idTxtEdadOtroIntegrante.setText("")

                contIntegrantes = contIntegrantes + 1
                dialogBinding.idConteoRegistradosAlerta.text = contIntegrantes.toString()
                if (contIntegrantes == numeroDeIntegrantes.toInt()) {
                    alerta_integrante.dismiss()
                    findNavController().navigate(R.id.action_datosSocialesFragment_to_datosProductivosFragment2)
                }
            }
        }
        alerta_integrante.show()
    }

    private fun instanciaFormularioAlerta() {
        edadA = dialogBinding.idTxtEdadOtroIntegrante.text.toString()
        val spinerGenero = dialogBinding.idSpinerGeneroOtroIntegrante
        val itemGenero = arrayOf("MUJER", "HOMBRE")
        val arrayAdapterGenero = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemGenero
        )
        spinerGenero.adapter = arrayAdapterGenero
        generoA = spinerGenero.selectedItem.toString()

        val spinerNivelAcademico = dialogBinding.idSpineNivelAcademicoOtroIntegrante
        val itemNivelAcademico = arrayOf("PRIMARIA", "SECUNDARIA", "PREGRADO", "POSGRADO")
        val arrayAdapterNivelAcademico = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemNivelAcademico
        )
        spinerNivelAcademico.adapter = arrayAdapterNivelAcademico
        nivelAcademicoA = spinerNivelAcademico.selectedItem.toString()

        val discapacidadRadioGroup = dialogBinding.idRadioGroupDiscapacidad
        discapacidadRadioGroup.setOnCheckedChangeListener { radioGroup, idRadioGruoup ->
            when (idRadioGruoup) {
                R.id.idSiDiscapacidadAlerta -> {
                    tieneDiscapacidad = "Si"
                    dialogBinding.idLayoutDiscapacidad.visibility = View.VISIBLE
                    esValidoAlerta = false
                    if (dialogBinding.idTxtDiscapacidadOtroIntegrante.text!!.isEmpty()) {
                        dialogBinding.idTxtDiscapacidadOtroIntegrante.error = "Campo requerido"
                        esValidoAlerta = false
                    } else {
                        dialogBinding.idTxtDiscapacidadOtroIntegrante.error = null
                    }
                }
                R.id.idNoDiscapacidadAlerta -> {
                    tieneDiscapacidad = "No"
                    dialogBinding.idLayoutDiscapacidad.visibility = View.GONE
                    esValidoAlerta = true
                }
            }
            discapacidad = dialogBinding.idTxtDiscapacidadOtroIntegrante.text.toString()
        }
    }

    private fun validarFormularioAlerta(): Boolean {
        instanciaFormularioAlerta()
        var esValidoAlertaAA = true

        if (!(dialogBinding.idSiDiscapacidadAlerta.isChecked || dialogBinding.idNoDiscapacidadAlerta.isChecked)) {
            dialogBinding.idErrorDiscapacidad.visibility = View.VISIBLE;
            esValidoAlertaAA = false
        } else {
            dialogBinding.idErrorDiscapacidad.visibility = View.GONE;
            esValidoAlertaAA = true
        }

        if (TextUtils.isEmpty(edadA)) {
            esValidoAlertaAA = false
            dialogBinding.idTxtEdadOtroIntegrante.error = "Campo requerido"
        } else {
            esValidoAlertaAA = true
            dialogBinding.idTxtEdadOtroIntegrante.error = null
        }
        return esValidoAlertaAA
    }
}