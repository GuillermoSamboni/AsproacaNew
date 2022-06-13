package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.modificarDatos.datosProductivos

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentTrabajadoresBinding
import com.asproaca.asproaca.modelos.AnimalesFinca
import com.campo.campocolombiano.design.constantes.Constantes2

class TrabajadoresModificarFragment : Fragment(R.layout.fragment_trabajadores) {
    private lateinit var binding: FragmentTrabajadoresBinding

    //DatosTrabajador
    private lateinit var cantidad_trabajadores_contratados: String
    private lateinit var tipo_contratacion: String
    private lateinit var cant_pago_dinero: String
    private lateinit var cant_pago_especie: String
    private lateinit var horario_laboral: String
    private lateinit var descripcion_adicional: String
    private lateinit var alojamiento_trabajadores: String
    private lateinit var estado_alojamiento_trabajadores: String

    private val listaDatosAnimales = mutableListOf<AnimalesFinca>()

    private lateinit var alert_anadir: AlertDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrabajadoresBinding.bind(view)

        instanciaDatosFormuario()
        binding.textView2.setText("MODIFICAR DATOS DE LOS TRABAJADORES")
        ponerDatos()
        binding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosFormulari()
        }

    }

    private fun ponerDatos() {
        cantidad_trabajadores_contratados =
            binding.idTxtCantidadTrabajadores.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.cantidad_trabajadores_contratados.toString())
                .toString()
        // tipo_contratacion = binding.idTxtCantidadTrabajadores.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.cantidad_trabajadores_contratados.toString()).toString()
        cant_pago_dinero =
            binding.idTxtCantidadPagoTrabajadores.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.cant_pago_dinero.toString())
                .toString()
        cant_pago_especie =
            binding.idTxtCantidadPagoEspecieTrabajadores.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.cant_pago_especie.toString())
                .toString()
        horario_laboral =
            binding.idTxtHorarioLavoral.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.horario_laboral.toString())
                .toString()
        descripcion_adicional =
            binding.idTxtDescripcionObservaciones.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.descripcion_adicional.toString())
                .toString()
        alojamiento_trabajadores =
            binding.idTxtAlojamientoTrabajadores.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.alojamiento_trabajadores.toString())
                .toString()
        //estado_alojamiento_trabajadores
    }

    private fun pasarDatosFormulari() {
        instanciaDatosFormuario()
        if (validarCamposFormularo()) {

            Constantes2.cantidad_trabajadores_contratados = cantidad_trabajadores_contratados
            Constantes2.tipo_contratacion = tipo_contratacion
            Constantes2.cant_pago_dinero = cant_pago_dinero
            Constantes2.cant_pago_especie = cant_pago_especie
            Constantes2.horario_laboral = horario_laboral
            Constantes2.descripcion_adicional = descripcion_adicional
            Constantes2.alojamiento_trabajadores = alojamiento_trabajadores
            Constantes2.estado_alojamiento_trabajadores = estado_alojamiento_trabajadores

            findNavController().navigate(R.id.action_trabajadoresFragment_to_datosAmbientalesFragment)

        }
    }


    private fun limpiarDatosFormulario() {
        binding.idTxtCantidadTrabajadores.setText("")

        binding.idTxtCantidadPagoTrabajadores.setText("")
        binding.idTxtCantidadPagoEspecieTrabajadores.setText("")
        binding.idTxtHorarioLavoral.setText("")

        binding.idTxtDescripcionObservaciones.setText("")
        binding.idTxtAlojamientoTrabajadores.setText("")


    }

    private fun instanciaDatosFormuario() {

        cantidad_trabajadores_contratados = binding.idTxtCantidadTrabajadores.text.toString()

        val spinerTipoContrato = binding.idSpinerTipoContrato
        val itemsTipoContrato = arrayOf(
            "Informales", "Ops", "Termino Indefinido",
            "Termino definido", "Sindical"
        )

        val arrayAdapterTipoContrato = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsTipoContrato
        )
        spinerTipoContrato.adapter = arrayAdapterTipoContrato
        tipo_contratacion = spinerTipoContrato.selectedItem.toString()


        cant_pago_dinero = binding.idTxtCantidadPagoTrabajadores.text.toString()
        cant_pago_especie = binding.idTxtCantidadPagoEspecieTrabajadores.text.toString()
        horario_laboral = binding.idTxtHorarioLavoral.text.toString()

        descripcion_adicional = binding.idTxtDescripcionObservaciones.text.toString()
        alojamiento_trabajadores = binding.idTxtAlojamientoTrabajadores.text.toString()


        val spinerEstadoAlojamiento = binding.idSpinerEstadoAlojamientoTrabajadores
        val itemsEstadoAlojamiento = arrayOf("Malo", "Regular", "Bueno")

        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsEstadoAlojamiento
        )
        spinerEstadoAlojamiento.adapter = arrayAdapterProveedor
        estado_alojamiento_trabajadores = spinerEstadoAlojamiento.selectedItem.toString()


    }

    private fun validarCamposFormularo(): Boolean {
        var esValido = true

        if (TextUtils.isEmpty(cantidad_trabajadores_contratados)) {
            binding.idTxtCantidadTrabajadores.error = "Campo requerido"
            esValido = false
        } else binding.idTxtCantidadTrabajadores.error = null

        if (TextUtils.isEmpty(cant_pago_dinero)) {
            binding.idTxtCantidadPagoTrabajadores.error = "Campo requerido"
            esValido = false
        } else binding.idTxtCantidadPagoTrabajadores.error = null

        if (TextUtils.isEmpty(cant_pago_especie)) {
            binding.idTxtCantidadPagoEspecieTrabajadores.error = "Campo requerido"
            esValido = false
        } else binding.idTxtCantidadPagoEspecieTrabajadores.error = null

        if (TextUtils.isEmpty(horario_laboral)) {
            binding.idTxtHorarioLavoral.error = "Campo requerido"
            esValido = false
        } else binding.idTxtHorarioLavoral.error = null

        if (TextUtils.isEmpty(alojamiento_trabajadores)) {
            binding.idTxtAlojamientoTrabajadores.error = "Campo requerido"
            esValido = false
        } else binding.idTxtAlojamientoTrabajadores.error = null

        return esValido
    }

}