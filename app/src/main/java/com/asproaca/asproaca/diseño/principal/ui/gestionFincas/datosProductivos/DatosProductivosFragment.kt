package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosProductivos

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
import com.asproaca.asproaca.databinding.AlertAnadirProductivosBinding
import com.asproaca.asproaca.databinding.FragmentDatosProductivosBinding
import com.asproaca.asproaca.modelos.Productivos
import com.campo.campocolombiano.design.constantes.Constantes2

class DatosProductivosFragment : Fragment(R.layout.fragment_datos_productivos) {
    private lateinit var binding: FragmentDatosProductivosBinding

    private var bodega_agroquimicos: String = ""
    private lateinit var nombre_producto: String
    private lateinit var area_productiva_total: String
    private var producto_certificado: String = ""
    private lateinit var certificado: String
    private lateinit var proveedor_semilla: String
    private var semilla_modificada: String = ""
    private lateinit var edad_producto: String
    private lateinit var a_tenido_plagas: String
    private lateinit var plagas: String
    private lateinit var a_tenido_enfermedades: String
    private lateinit var enfermedades: String

    private val listaProductos = mutableListOf<Productivos>()

    private lateinit var datosProductivos: Productivos

    private lateinit var alert_anadir: AlertDialog
    private lateinit var dialogBinding: AlertAnadirProductivosBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosProductivosBinding.bind(view)

        instanciaDatosFormulario()
        binding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosProductivos()
        }
    }

    private fun instanciaDatosFormulario() {
        val radioGroupBodega = binding.idRadioGroupAgroquimicos
        radioGroupBodega.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTieneAgroquimicos -> {
                    bodega_agroquimicos = "Si"
                }
                R.id.idNoTieneAgroquimicos -> {
                    bodega_agroquimicos = "No"
                }
            }
        }

        val spinerProducto = binding.idSpinerProductos
        val itemsProducto = arrayOf(
            "CAFÉ",
            "CAÑA",
            "CACAO",
            "HORTALIZAS",
            "PAPA",
            "TOMATE",
            "FRUTAL",
            "OTRO",
        )
        val arrayAdapterProducto = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsProducto
        )
        spinerProducto.adapter = arrayAdapterProducto
        nombre_producto = spinerProducto.selectedItem.toString()

        area_productiva_total = binding.idTxtAreaProductivaTota.text.toString()


        /**
        val radioGroupProdCertificado = binding.idRadioGroupAgroquimicos
        radioGroupProdCertificado.setOnCheckedChangeListener { radioGroup, idRadio ->
        when (idRadio) {
        R.id.idSiProductoCertificado -> {
        producto_certificado = "Si"
        }
        R.id.idNoProductoCertificado -> {
        producto_certificado = "No"
        }
        }
        }
         */

        /**
        val spinerCertificado = binding.idSpinerCertificado
        val itemsCertificado = arrayOf(
        "RAINFOREST",
        "DPA",
        "ORGANICO",
        "COMERCIO JUSTO",
        "PRACTICES",
        "OTRO",
        )
        val arrayAdapterCertificado = ArrayAdapter(
        requireContext(),
        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
        itemsCertificado
        )
        spinerCertificado.adapter = arrayAdapterCertificado
        certificado = spinerCertificado.selectedItem.toString()
         */
        val spinerProveedor = binding.idSpinerProvedorSemilla
        val itemsProveedor = arrayOf(
            "Proveedor 1",
            "Proveedor 2",
            "Proveedor 3",
            "Proveedor 3",
            "Proveedor 3",
            "Otro"
        )
        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsProveedor
        )
        spinerProveedor.adapter = arrayAdapterProveedor
        proveedor_semilla = spinerProveedor.selectedItem.toString()

        val radioGroupSemillaModificada = binding.idRadioGroupSemillasModificadas
        radioGroupSemillaModificada.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiSemillasModificadas -> {
                    semilla_modificada = "Si"
                }
                R.id.idNoSemillasModificadas -> {
                    semilla_modificada = "No"
                }
            }
        }
        plagas = binding.idTxtTipoPlagas.text.toString()

        val radioGroupAtenidoPlagas = binding.idRadioGroupATenidoPlagas
        radioGroupAtenidoPlagas.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiATenidoPlagas -> {
                    a_tenido_plagas = "Si"
                    binding.idLayoutTipoPlagas.visibility = View.VISIBLE
                    if (TextUtils.isEmpty(plagas)) {
                        binding.idTxtTipoPlagas.error = "Campo requerido"
                        binding.idTxtTipoPlagas.setText("")
                    } else binding.idTxtTipoPlagas.error = null
                }
                R.id.idNoATenidoPlagas -> {
                    a_tenido_plagas = "No"
                    binding.idLayoutTipoPlagas.visibility = View.GONE
                    plagas = ""
                }
            }
        }

        enfermedades = binding.idTxtEnfermedades.text.toString()
        val radioGroupEnfermedades = binding.idRadioGroupEnfermedades
        radioGroupEnfermedades.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEnfermedades -> {
                    a_tenido_enfermedades = "Si"
                    binding.idLayoutEnfermedades.visibility = View.VISIBLE
                    if (TextUtils.isEmpty(enfermedades)) {
                        binding.idTxtEnfermedades.error = "Campo requerido"
                        binding.idTxtEnfermedades.setText("")
                    } else binding.idTxtEnfermedades.error = null
                }
                R.id.idNoEnfermedades -> {
                    a_tenido_enfermedades = "No"
                    binding.idLayoutEnfermedades.visibility = View.GONE
                    enfermedades = ""
                }
            }
        }
        edad_producto = binding.idTxtEdadProducto.text.toString()

    }

    private fun instanciaDatosFormularioAlerta() {
        val radioGroupBodega = dialogBinding.idRadioGroupAgroquimicosA
        radioGroupBodega.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiTieneAgroquimicosA -> {
                    bodega_agroquimicos = "Si"
                }
                R.id.idNoTieneAgroquimicosA -> {
                    bodega_agroquimicos = "No"
                }
            }
        }

        val spinerProducto = dialogBinding.idSpinerProductosA
        val itemsProducto = arrayOf(
            "Café",
            "Caña",
            "Cacao",
            "Hortalizas",
            "Papa",
            "Tomate",
            "Frutal",
            "Tomate",
            "Otro cultivo",
        )
        val arrayAdapterProducto = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsProducto
        )
        spinerProducto.adapter = arrayAdapterProducto
        nombre_producto = spinerProducto.selectedItem.toString()

        area_productiva_total = dialogBinding.idTxtAreaProductivaTotaA.text.toString()
        /**
        val radioGroupProdCertificado = dialogBinding.idRadioGroupAgroquimicosA
        radioGroupProdCertificado.setOnCheckedChangeListener { radioGroup, idRadio ->
        when (idRadio) {
        R.id.idSiProductoCertificado -> {
        producto_certificado = "Si"
        }
        R.id.idNoProductoCertificado -> {
        producto_certificado = "No"
        }
        }
        }
         */

        /**
        val spinerCertificado = dialogBinding.idSpinerCertificadoA
        val itemsCertificado = arrayOf(
        "Rainforest",
        "bpa",
        "organico",
        "comercio justo",
        "Otro",
        )
        val arrayAdapterCertificado = ArrayAdapter(
        requireContext(),
        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
        itemsCertificado
        )
        spinerCertificado.adapter = arrayAdapterCertificado
        certificado = spinerCertificado.selectedItem.toString()
         */
        val spinerProveedor = dialogBinding.idSpinerProvedorSemillaA
        val itemsProveedor = arrayOf(
            "Proveedor 1",
            "Proveedor 2",
            "Proveedor 3",
            "Proveedor 3",
            "Proveedor 3",
            "Otro"
        )
        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsProveedor
        )
        spinerProveedor.adapter = arrayAdapterProveedor
        proveedor_semilla = spinerProveedor.selectedItem.toString()

        val radioGroupSemillaModificada = dialogBinding.idRadioGroupSemillasModificadasA
        radioGroupSemillaModificada.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiSemillasModificadasA -> {
                    semilla_modificada = "Si"
                }
                R.id.idNoSemillasModificadasA -> {
                    semilla_modificada = "No"
                }
            }
        }
        edad_producto = dialogBinding.idTxtEdadProductoA.text.toString()
        plagas = dialogBinding.idTxtTipoPlagasA.text.toString()

    }

    private fun pasarDatosProductivos() {
        instanciaDatosFormulario()
        datosProductivos = Productivos(
            bodega_agroquimicos,
            nombre_producto,
            area_productiva_total,
            //producto_certificado,
            //certificado,
            proveedor_semilla,
            semilla_modificada,
            edad_producto,
            plagas
        )

        if (validarCamposFormulario()) {
            listaProductos.addAll(listOf(datosProductivos))
            Constantes2.listaProductivos = listaProductos
            limpiarCamposFormulario()
            anadirOtraPersona()
        }

    }

    private fun anadirOtraPersona() {
        val alert_persona = AlertDialog.Builder(requireContext())
        alert_persona.setTitle("Añadir datos Productivos")
        alert_persona.setMessage("¿Desea agregar otro producto?")
        alert_persona.setCancelable(false)
        alert_persona.setPositiveButton("Agregar") { _, _ ->
            alert_persona.setCancelable(true)
            mostrarFormulario()
            limpiarCamposFormulario()
        }
        alert_persona.setNegativeButton("No, Continuar") { _, _ ->
            alert_persona.setCancelable(true)
            //alert_anadir.dismiss()
            findNavController().navigate(R.id.action_datosProductivosFragment_to_produccionFragment)
        }
        alert_persona.create().show()
    }

    private fun limpiarCamposFormulario() {
        binding.idTxtAreaProductivaTota.setText("")
        binding.idTxtEdadProducto.setText("")
        binding.idTxtTipoPlagas.setText("")
    }

    private fun mostrarFormulario() {
        dialogBinding = AlertAnadirProductivosBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        instanciaDatosFormulario()

        Productivos(
            bodega_agroquimicos,
            nombre_producto,
            dialogBinding.idTxtAreaProductivaTotaA.text.toString(),
            //producto_certificado,
            //certificado,
            proveedor_semilla,
            semilla_modificada,
            dialogBinding.idTxtEdadProductoA.text.toString(),
            dialogBinding.idTxtTipoPlagasA.text.toString()
        )
        instanciaDatosFormularioAlerta()
        dialogBinding.idBtnContinuarProcesoA.setOnClickListener {
            agregarDatosAlerta()
        }
        alert_anadir.setCancelable(true)
        alert_anadir.window?.setBackgroundDrawableResource(R.color.color_transparent)

        alert_anadir.show()
    }

    private fun agregarDatosAlerta() {
        instanciaDatosFormularioAlerta()
        datosProductivos = Productivos(
            bodega_agroquimicos,
            nombre_producto,
            dialogBinding.idTxtAreaProductivaTotaA.text.toString(),
            //producto_certificado,
            //certificado,
            proveedor_semilla,
            semilla_modificada,
            dialogBinding.idTxtEdadProductoA.text.toString(),
            dialogBinding.idTxtTipoPlagasA.text.toString()
        )
        instanciaDatosFormularioAlerta()
        if (validarCamposFormularioAlerta()) {

            listaProductos.addAll(listOf(datosProductivos))
            Constantes2.listaProductivos = listaProductos
            anadirOtraPersona()
            limpiarCamposFormularioAlerta()
            alert_anadir.dismiss()

        }
    }

    private fun limpiarCamposFormularioAlerta() {
        dialogBinding.idTxtAreaProductivaTotaA.setText("")
        dialogBinding.idTxtEdadProductoA.setText("")
        dialogBinding.idTxtTipoPlagasA.setText("")
    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true

        if (!(binding.idSiTieneAgroquimicos.isChecked || binding.idNoTieneAgroquimicos.isChecked)) {
            esValido = false
            binding.idErrorBodega.visibility = View.VISIBLE
        } else {
            binding.idErrorBodega.visibility = View.GONE
        }
        /**
        if (!(binding.idSiProductoCertificado.isChecked || binding.idNoProductoCertificado.isChecked)) {
        esValido = false
        binding.idErrorCertificado.visibility = View.VISIBLE
        } else {
        binding.idErrorCertificado.visibility = View.GONE
        }*/

        if (!(binding.idSiSemillasModificadas.isChecked || binding.idNoSemillasModificadas.isChecked)) {
            esValido = false
            binding.idErrorModificada.visibility = View.VISIBLE
        } else {
            binding.idErrorModificada.visibility = View.GONE
        }

        if (!(binding.idSiATenidoPlagas.isChecked || binding.idNoATenidoPlagas.isChecked)) {
            esValido = false
            binding.idErrorHaTenidoPlagas.visibility = View.VISIBLE
        } else {
            binding.idErrorHaTenidoPlagas.visibility = View.GONE
        }

        if (TextUtils.isEmpty(area_productiva_total)) {
            esValido = false
            binding.idTxtAreaProductivaTota.error = "Campo requerido"
        } else binding.idTxtAreaProductivaTota.error = null

        if (TextUtils.isEmpty(edad_producto)) {
            esValido = false
            binding.idTxtEdadProducto.error = "Campo requerido"
        } else binding.idTxtEdadProducto.error = null


        return esValido
    }

    private fun validarCamposFormularioAlerta(): Boolean {
        var esValido = true
        if (!(dialogBinding.idSiTieneAgroquimicosA.isChecked || dialogBinding.idNoTieneAgroquimicosA.isChecked)) {
            esValido = false
            dialogBinding.idErrorBodegaA.visibility = View.VISIBLE
        } else {
            dialogBinding.idErrorBodegaA.visibility = View.GONE
        }

        /**
        if (!(dialogBinding.idSiProductoCertificadoA.isChecked || dialogBinding.idNoProductoCertificadoA.isChecked)) {
        esValido = false
        dialogBinding.idErrorCertificadoA.visibility = View.VISIBLE
        } else {
        dialogBinding.idErrorCertificadoA.visibility = View.GONE
        }
         */
        if (!(dialogBinding.idSiSemillasModificadasA.isChecked || dialogBinding.idNoSemillasModificadasA.isChecked)) {
            esValido = false
            dialogBinding.idErrorModificadaA.visibility = View.VISIBLE
        } else {
            dialogBinding.idErrorModificadaA.visibility = View.GONE
        }

        if (TextUtils.isEmpty(area_productiva_total)) {
            esValido = false
            dialogBinding.idTxtAreaProductivaTotaA.error = "Campo requerido"
        } else dialogBinding.idTxtAreaProductivaTotaA.error = null

        if (TextUtils.isEmpty(edad_producto)) {
            esValido = false
            dialogBinding.idTxtEdadProductoA.error = "Campo requerido"
        } else dialogBinding.idTxtEdadProductoA.error = null

        if (TextUtils.isEmpty(plagas)) {
            esValido = false
            dialogBinding.idTxtTipoPlagasA.error = "Campo requerido"
        } else dialogBinding.idTxtTipoPlagasA.error = null

        return esValido
    }


}

