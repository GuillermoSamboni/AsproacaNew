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
    private var tiene_alojamiento: String = ""
    private var alojamiento_trabajadores: String = ""
    private lateinit var estado_alojamiento_trabajadores: String

    private val listaDatosAnimales = mutableListOf<AnimalesFinca>()

    private lateinit var alert_anadir: AlertDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrabajadoresBinding.bind(view)

        instanciaDatosFormuario()
        val titulo = binding.textView2
        if (Constantes2.crearNuevaFinca == true){
            titulo.apply { this.setText("MODIFICAR DATOS DE LOS TRABAJADORES") }
        }else{
            titulo.apply { this.setText("ACTUALIZAR DATOS DE LOS TRABAJADORES") }
        }

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
        descripcion_adicional =
            binding.idTxtDescripcionObservaciones.setText(Constantes2.listaDatosFinca!!.datos_trabajadores!!.descripcion_adicional.toString())
                .toString()
        if (Constantes2.listaDatosFinca!!.datos_trabajadores!!.tiene_alojamiento == "Si") {
            binding.idSiAlojamiento.isChecked = true
            tiene_alojamiento = "Si"
        } else {
            binding.idSiAlojamiento.isChecked = true
            tiene_alojamiento = "No"
        }
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
            Constantes2.tiene_alojamiento = tiene_alojamiento
            Constantes2.alojamiento_trabajadores = alojamiento_trabajadores
            Constantes2.estado_alojamiento_trabajadores = estado_alojamiento_trabajadores

            //findNavController().navigate(R.id.action_trabajadoresFragment_to_datosAmbientalesFragment)
            findNavController().navigate(R.id.action_trabajadoresFragment_to_datosAmbientalesEditaFragment)

        }
    }


    private fun instanciaDatosFormuario() {

        cantidad_trabajadores_contratados = binding.idTxtCantidadTrabajadores.text.toString()

        val spinerTipoContrato = binding.idSpinerHorarioLavoral
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

        descripcion_adicional = binding.idTxtDescripcionObservaciones.text.toString()

        val spinerHorarioLavoral = binding.idSpinerTipoContrato
        val itemsHorarioLavoral = arrayOf(
            "2", "4", "6",
            "8", "OTRO"
        )

        val arrayAdapterHorariolavoral = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsHorarioLavoral
        )
        spinerHorarioLavoral.adapter = arrayAdapterHorariolavoral
        horario_laboral = spinerHorarioLavoral.selectedItem.toString()

        val spinerEstadoAlojamiento = binding.idSpinerEstadoAlojamientoTrabajadores
        val itemsEstadoAlojamiento = arrayOf("MALO", "REGULAR", "BUENO")

        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsEstadoAlojamiento
        )
        spinerEstadoAlojamiento.adapter = arrayAdapterProveedor

        val radioGroupAlojamiento = binding.idRadioGroupAlojamiento
        radioGroupAlojamiento.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.idSiAlojamiento -> {
                    tiene_alojamiento = "Si"
                    binding.idLayoutAlojamientoTrabajadores.visibility = View.VISIBLE
                    alojamiento_trabajadores = binding.idTxtAlojamientoTrabajadores.text.toString()
                }
                R.id.idNoAlojamiento -> {
                    tiene_alojamiento = "No"
                    binding.idLayoutAlojamientoTrabajadores.visibility = View.GONE
                    alojamiento_trabajadores = ""
                }
            }
        }
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

        if (!(binding.idSiAlojamiento.isChecked || binding.idNoAlojamiento.isChecked)) {
            esValido = false
            binding.idErrorAlojamiento.visibility = View.VISIBLE
        } else {
            esValido = true
            binding.idErrorAlojamiento.visibility = View.GONE
        }
        return esValido
    }

}