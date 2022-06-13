package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.modificarDatos.datosProductivos

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.IntegrantesAdapter
import com.asproaca.asproaca.adaptadores.ProduccionAgicolaAdapter
import com.asproaca.asproaca.databinding.*
import com.asproaca.asproaca.modelos.IntegrantesFamilia
import com.asproaca.asproaca.modelos.LotesProduccion
import com.asproaca.asproaca.modelos.Productivos
import com.campo.campocolombiano.design.constantes.Constantes2

class DatosProductivosModificarFragment : Fragment(R.layout.fragment_datos_productivos_modificar) {
    private lateinit var binding: FragmentDatosProductivosModificarBinding
    private lateinit var myProductivosAdapter: ProduccionAgicolaAdapter
    private lateinit var idRecyclerView: RecyclerView

    private lateinit var nombre_producto: String
    private var bodega_agroquimicos: String = ""
    private lateinit var area_productiva_total: String
    private var proveedor_semilla: String = ""
    private var semilla_modificada: String = ""
    private lateinit var edad_producto: String
    private var a_tenido_plagas: String = ""
    private lateinit var plagas: String
    private var a_tenido_enfermedades: String = ""
    private lateinit var enfermedades: String
    private lateinit var fertilizantes_usados: String
    private lateinit var agroquimicos_usados: String
    private var equipo_proteccion: String? = null
    private lateinit var equipo_proteccion_estado: String
    private var cuenta_con_infraestructura: String? = null
    private lateinit var estado_infraestrcutura: String
    private lateinit var tipo_de_secado_de_cafe: String
    private lateinit var equipos_industriales: String
    private lateinit var numero_lavados: String
    private var cantidadLotes = "0"
    private var listLotes = mutableListOf<LotesProduccion>()
    private lateinit var lotes: LotesProduccion
    private lateinit var numero_de_arboles: String
    private lateinit var edad_del_lote: String
    private lateinit var variedad: String
    var contLotes = 0
    private val listaProductos = mutableListOf<Productivos>()
    private lateinit var datosProductivos: Productivos
    private lateinit var alert_anadir: AlertDialog
    private lateinit var dialogBinding: AlertaLotesBinding
    private lateinit var productivosBinding: AlertAnadirProductivosBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosProductivosModificarBinding.bind(view)

        initRecyclerView()

        /**try {
        instanciaDatosFormulario()
        ponerDatos()
        } catch (e: Exception) {
        Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }

        binding.idBtnContinuarProceso.setOnClickListener {
        pasarDatosProductivos()
        }*/
    }
/*
    private fun ponerDatos() {
        //nombre_producto = binding.idTxt toString()
        Constantes2.listaDatosFinca!!.datos_productivos!!.forEach {
            area_productiva_total =
                binding.idTxtAreaProductivaTota.setText(it.area_productiva_total).toString()
            edad_producto = binding.idTxtEdadProducto.setText(it.edad_producto).toString()

            if (it.bodega_agroquimicos == "Si") {
                binding.idSiTieneAgroquimicos.isChecked = true
                bodega_agroquimicos = "Si"
            } else {
                binding.idSiTieneAgroquimicos.isChecked = true
                bodega_agroquimicos = "No"
            }
            //proveedor_semilla = binding.toString()

            if (it.bodega_agroquimicos == "Si") {
                binding.idSiSemillasModificadas.isChecked = true
                semilla_modificada = "Si"
            } else {
                binding.idNoSemillasModificadas.isChecked = true
                semilla_modificada = "No"
            }

            if (it.tiene_plagas == "Si") {
                binding.idSiATenidoPlagas.isChecked = true
                a_tenido_plagas = "Si"
            } else {
                binding.idNoATenidoPlagas.isChecked = true
                a_tenido_plagas = "No"
            }
            plagas = binding.idTxtTipoPlagas.setText(it.plagas).toString()

            if (it.tiene_plagas == "Si") {
                binding.idSiEnfermedades.isChecked = true
                a_tenido_enfermedades = "Si"
            } else {
                binding.idNoEnfermedades.isChecked = true
                a_tenido_enfermedades = "No"
            }
            enfermedades = binding.idTxtEnfermedades.setText(it.enfermedades).toString()
            fertilizantes_usados =
                binding.idTxtFertilizantesUsados.setText(it.fertilizantes).toString()
            agroquimicos_usados =
                binding.idTxtAgroquimicosUsados.setText(it.agroquimicos).toString()

            if (it.usa_proteccion == "Si") {
                binding.idSiEquiposProteccion.isChecked = true
                equipo_proteccion = "Si"
            } else {
                binding.idNoEquiposProteccion.isChecked = true
                equipo_proteccion = "No"
            }

            if (it.tiene_infraestructura == "Si") {
                binding.idSiEquiposInfraestructura.isChecked = true
                cuenta_con_infraestructura = "Si"
            } else {
                binding.idNoEquiposInfraestructura.isChecked = true
                cuenta_con_infraestructura = "No"
            }
            //estado_infraestrcutura = binding.toString()

            //tipo_de_secado_de_cafe = binding.toString()

            equipos_industriales =
                binding.idTxtEquiposIndustriales.setText(it.equipos_idustriales).toString()
            numero_lavados = binding.idTxtNumeroLavadosCafe.setText(it.cantidad_lavados).toString()
            cantidadLotes = binding.idTxtCantidadLotes.setText(it.cantidadLotes).toString()

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

        val spinerProveedor = binding.idSpinerProvedorSemilla
        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            Constantes2.listaProveedores!!
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
        plagas = binding.idTxtTipoPlagas.text.toString()

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
        enfermedades = binding.idTxtEnfermedades.text.toString()

        edad_producto = binding.idTxtEdadProducto.text.toString()
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

        cantidadLotes = binding.idTxtCantidadLotes.text.toString()
    }

    private fun pasarDatosProductivos() {
        instanciaDatosFormulario()
        datosProductivos = Productivos(
            nombre_producto,
            area_productiva_total,
            edad_producto,
            bodega_agroquimicos,
            proveedor_semilla,
            semilla_modificada,
            a_tenido_plagas,
            plagas,
            a_tenido_enfermedades,
            enfermedades,
            fertilizantes_usados,
            agroquimicos_usados,
            equipo_proteccion,
            equipo_proteccion_estado,
            cuenta_con_infraestructura,
            estado_infraestrcutura,
            tipo_de_secado_de_cafe,
            equipos_industriales,
            numero_lavados,
            cantidadLotes,
            listLotes
        )
        if (validarCamposFormulario()) {
            Constantes2.listaDatosFinca!!.datos_productivos!!.forEach {
                if (binding.idTxtCantidadLotes.text.toString() == it.cantidadLotes) {
                    listaProductos.addAll(listOf(datosProductivos))
                    Constantes2.listaProductivos = listaProductos
                    anadirOtroDatoAgricola()
                } else {
                    anadirOtroDatoAgricola()
                }
            }

        }

    }

    private fun pasarDatosProductivosAlerta() {
        instanciaDatosFormularioAlerta()
        datosProductivos = Productivos(
            nombre_producto,
            area_productiva_total,
            edad_producto,
            bodega_agroquimicos,
            proveedor_semilla,
            semilla_modificada,
            a_tenido_plagas,
            plagas,
            a_tenido_enfermedades,
            enfermedades,
            fertilizantes_usados,
            agroquimicos_usados,
            equipo_proteccion,
            equipo_proteccion_estado,
            cuenta_con_infraestructura,
            estado_infraestrcutura,
            tipo_de_secado_de_cafe,
            equipos_industriales,
            numero_lavados,
            cantidadLotes,
            listLotes
        )
        if (validarCamposFormularioAlerta()) {
            if (cantidadLotes == "0") {
                listaProductos.addAll(listOf(datosProductivos))
                Constantes2.listaProductivos = listaProductos
                anadirOtroDatoAgricola()
                Log.e("Informacion", Constantes2.listaProductivos.toString())
            }
            if (cantidadLotes != "0") {
                mostrarFormularioLotes()
            }
        }
    }

    private fun mostrarFormularioLotes() {
        dialogBinding = AlertaLotesBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        instanciaFormularioLotes()

        alert_anadir.setCancelable(false)
        alert_anadir.show()
    }

    private fun anadirOtroDatoAgricola() {
        val alert_persona = AlertDialog.Builder(requireContext())
        alert_persona.setTitle("Añadir datos de producción agricola")
        alert_persona.setMessage("¿Desea agregar otro cultivo?")
        alert_persona.setCancelable(false)
        alert_persona.setPositiveButton("Agregar") { _, _ ->
            alert_persona.setCancelable(true)
            mostrarFormulario()

        }
        alert_persona.setNegativeButton("No, Continuar") { _, _ ->
            alert_persona.setCancelable(true)
            //alert_anadir.dismiss()
            findNavController().navigate(R.id.action_datosProductivosFragment_to_animalesFragment2)
        }
        alert_persona.create().show()
    }

    private fun mostrarFormulario() {

        productivosBinding = AlertAnadirProductivosBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(productivosBinding.root)
        }.create()
        instanciaDatosFormularioAlerta()

        alert_anadir.setCancelable(true)
        alert_anadir.window?.setBackgroundDrawableResource(R.color.color_transparent)

        alert_anadir.show()
    }

    private fun instanciaDatosFormularioAlerta() {
        val radioGroupBodega = productivosBinding.idRadioGroupAgroquimicosA
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

        val spinerProducto = productivosBinding.idSpinerProductosA
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

        area_productiva_total = productivosBinding.idTxtAreaProductivaTotaA.text.toString()

        val spinerProveedor = binding.idSpinerProvedorSemilla
        val arrayAdapterProveedor = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            Constantes2.listaProveedores!!
        )
        spinerProveedor.adapter = arrayAdapterProveedor
        proveedor_semilla = spinerProveedor.selectedItem.toString()

        val radioGroupSemillaModificada = productivosBinding.idRadioGroupSemillasModificadasA
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
        plagas = productivosBinding.idTxtTipoPlagasA.text.toString()

        val radioGroupAtenidoPlagas = productivosBinding.idRadioGroupATenidoPlagasA
        radioGroupAtenidoPlagas.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiATenidoPlagasA -> {
                    a_tenido_plagas = "Si"
                    productivosBinding.idLayoutTipoPlagasA.visibility = View.VISIBLE
                    if (TextUtils.isEmpty(plagas)) {
                        productivosBinding.idTxtTipoPlagasA.error = "Campo requerido"
                        productivosBinding.idTxtTipoPlagasA.setText("")
                    } else productivosBinding.idTxtTipoPlagasA.error = null
                }
                R.id.idNoATenidoPlagasA -> {
                    a_tenido_plagas = "No"
                    productivosBinding.idLayoutTipoPlagasA.visibility = View.GONE
                    plagas = ""
                }
            }
        }

        enfermedades = productivosBinding.idTxtEnfermedadesA.text.toString()
        val radioGroupEnfermedades = productivosBinding.idRadioGroupEnfermedadesA
        radioGroupEnfermedades.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEnfermedadesA -> {
                    a_tenido_enfermedades = "Si"
                    productivosBinding.idLayoutEnfermedadesA.visibility = View.VISIBLE
                    if (TextUtils.isEmpty(enfermedades)) {
                        productivosBinding.idTxtEnfermedadesA.error = "Campo requerido"
                        productivosBinding.idTxtEnfermedadesA.setText("")
                    } else productivosBinding.idTxtEnfermedadesA.error = null
                }
                R.id.idNoEnfermedadesA -> {
                    a_tenido_enfermedades = "No"
                    productivosBinding.idLayoutEnfermedadesA.visibility = View.GONE
                    enfermedades = ""
                }
            }
        }
        edad_producto = productivosBinding.idTxtEdadProductoA.text.toString()
        fertilizantes_usados = productivosBinding.idTxtFertilizantesUsadosA.text.toString()
        agroquimicos_usados = productivosBinding.idTxtAgroquimicosUsadosA.text.toString()

        val radioGroupProteccion = productivosBinding.idRadioGroupEquiposProteccionA
        radioGroupProteccion.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEquiposProteccionA -> {
                    equipo_proteccion = "Si"
                }
                R.id.idNoEquiposProteccionA -> {
                    equipo_proteccion = "No"
                }
            }
        }

        val spinerEstadoProteccion = productivosBinding.idSpinerEstadoEquiposProteccionA
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

        val radioGroupInfraestructura = productivosBinding.idRadioGroupEquiposInfraestructuraA
        radioGroupInfraestructura.setOnCheckedChangeListener { radioGroup, idRadio ->
            when (idRadio) {
                R.id.idSiEquiposInfraestructuraA -> {
                    cuenta_con_infraestructura = "Si"
                }
                R.id.idNoEquiposInfraestructuraA -> {
                    cuenta_con_infraestructura = "No"
                }
            }
        }

        val spinerEstadoInfraestructura = productivosBinding.idSpinerEstadoEquiposInfraestructuraA
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

        val spinerTipoSecadoCafe = productivosBinding.idSpinerTipoSecadocafeA
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

        equipos_industriales = productivosBinding.idTxtEquiposIndustrialesA.text.toString()
        numero_lavados = productivosBinding.idTxtNumeroLavadosCafeA.text.toString()

        cantidadLotes = productivosBinding.idTxtCantidadLotesA.text.toString()

        productivosBinding.idBtnContinuarProceso.setOnClickListener {
            pasarDatosProductivosAlerta()
        }
    }

    private fun validarCamposFormulario(): Boolean {
        var esValido = true

        if (!(binding.idSiTieneAgroquimicos.isChecked || binding.idNoTieneAgroquimicos.isChecked)) {
            esValido = false
            binding.idErrorBodega.visibility = View.VISIBLE
        } else {
            binding.idErrorBodega.visibility = View.GONE
        }

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
        plagas = binding.idTxtTipoPlagas.text.toString()

        if (!(binding.idSiEnfermedades.isChecked || binding.idNoEnfermedades.isChecked)) {
            esValido = false
            binding.idErrorEnfermedades.visibility = View.VISIBLE
        } else {
            binding.idErrorEnfermedades.visibility = View.GONE
        }

        enfermedades = binding.idTxtEnfermedades.text.toString()
        if (TextUtils.isEmpty(area_productiva_total)) {
            esValido = false
            binding.idTxtAreaProductivaTota.error = "Campo requerido"
        } else binding.idTxtAreaProductivaTota.error = null

        if (TextUtils.isEmpty(edad_producto)) {
            esValido = false
            binding.idTxtEdadProducto.error = "Campo requerido"
        } else binding.idTxtEdadProducto.error = null

        if (TextUtils.isEmpty(fertilizantes_usados)) {
            esValido = false
            binding.idTxtFertilizantesUsados.error = "Campo requerido"
        } else binding.idTxtFertilizantesUsados.error = null

        if (TextUtils.isEmpty(agroquimicos_usados)) {
            esValido = false
            binding.idTxtAgroquimicosUsados.error = "Campo requerido"
        } else binding.idTxtAgroquimicosUsados.error = null

        if (!(binding.idSiEquiposProteccion.isChecked || binding.idNoEquiposProteccion.isChecked)) {
            esValido = false
            binding.idErrorUsaEquiposProteccion.visibility = View.VISIBLE
        } else {
            binding.idErrorUsaEquiposProteccion.visibility = View.GONE
        }

        if (!(binding.idSiEquiposInfraestructura.isChecked || binding.idNoEquiposInfraestructura.isChecked)) {
            esValido = false
            binding.idErrorUsaEquiposPoscosecha.visibility = View.VISIBLE
        } else {
            binding.idErrorUsaEquiposPoscosecha.visibility = View.GONE
        }

        if (TextUtils.isEmpty(equipos_industriales)) {
            esValido = false
            binding.idTxtEquiposIndustriales.error = "Campo requerido"
        } else binding.idTxtEquiposIndustriales.error = null

        if (TextUtils.isEmpty(numero_lavados)) {
            esValido = false
            binding.idTxtNumeroLavadosCafe.error = "Campo requerido"
        } else binding.idTxtNumeroLavadosCafe.error = null

        cantidadLotes = binding.idTxtCantidadLotes.text.toString()


        return esValido
    }

    private fun validarCamposFormularioAlerta(): Boolean {
        var esValido = true

        if (!(productivosBinding.idSiTieneAgroquimicosA.isChecked || productivosBinding.idNoTieneAgroquimicosA.isChecked)) {
            esValido = false
            productivosBinding.idErrorBodegaA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorBodegaA.visibility = View.GONE
        }
        if (!(productivosBinding.idSiSemillasModificadasA.isChecked || productivosBinding.idNoSemillasModificadasA.isChecked)) {
            esValido = false
            productivosBinding.idErrorModificadaA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorModificadaA.visibility = View.GONE
        }
        if (!(productivosBinding.idSiATenidoPlagasA.isChecked || productivosBinding.idNoATenidoPlagasA.isChecked)) {
            esValido = false
            productivosBinding.idErrorHaTenidoPlagasA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorHaTenidoPlagasA.visibility = View.GONE
        }
        plagas = productivosBinding.idTxtTipoPlagasA.text.toString()

        if (!(productivosBinding.idSiEnfermedadesA.isChecked || productivosBinding.idNoEnfermedadesA.isChecked)) {
            esValido = false
            productivosBinding.idErrorEnfermedadesA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorEnfermedadesA.visibility = View.GONE
        }
        enfermedades = productivosBinding.idTxtEnfermedadesA.text.toString()
        if (TextUtils.isEmpty(area_productiva_total)) {
            esValido = false
            productivosBinding.idTxtAreaProductivaTotaA.error = "Campo requerido"
        } else productivosBinding.idTxtAreaProductivaTotaA.error = null

        if (TextUtils.isEmpty(edad_producto)) {
            esValido = false
            productivosBinding.idTxtEdadProductoA.error = "Campo requerido"
        } else productivosBinding.idTxtEdadProductoA.error = null

        if (TextUtils.isEmpty(fertilizantes_usados)) {
            esValido = false
            productivosBinding.idTxtFertilizantesUsadosA.error = "Campo requerido"
        } else productivosBinding.idTxtFertilizantesUsadosA.error = null

        if (TextUtils.isEmpty(agroquimicos_usados)) {
            esValido = false
            productivosBinding.idTxtAgroquimicosUsadosA.error = "Campo requerido"
        } else productivosBinding.idTxtAgroquimicosUsadosA.error = null

        if (!(productivosBinding.idSiEquiposProteccionA.isChecked || productivosBinding.idNoEquiposProteccionA.isChecked)) {
            esValido = false
            productivosBinding.idErrorUsaEquiposProteccionA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorUsaEquiposProteccionA.visibility = View.GONE
        }
        if (!(productivosBinding.idSiEquiposInfraestructuraA.isChecked || productivosBinding.idNoEquiposInfraestructuraA.isChecked)) {
            esValido = false
            productivosBinding.idErrorUsaEquiposPoscosechaA.visibility = View.VISIBLE
        } else {
            productivosBinding.idErrorUsaEquiposPoscosechaA.visibility = View.GONE
        }
        if (TextUtils.isEmpty(equipos_industriales)) {
            esValido = false
            productivosBinding.idTxtEquiposIndustrialesA.error = "Campo requerido"
        } else productivosBinding.idTxtEquiposIndustrialesA.error = null

        if (TextUtils.isEmpty(numero_lavados)) {
            esValido = false
            productivosBinding.idTxtNumeroLavadosCafeA.error = "Campo requerido"
        } else productivosBinding.idTxtNumeroLavadosCafeA.error = null

        cantidadLotes = productivosBinding.idTxtCantidadLotesA.text.toString()


        return esValido
    }

    private fun instanciaFormularioLotes() {
        numero_de_arboles = dialogBinding.idTxtNumeroArbolesAlerta.text.toString()
        edad_del_lote = dialogBinding.idTxtEdadLoteAlerta.text.toString()

        dialogBinding.idConteoAlerta.setText(cantidadLotes)

        val spinerVariedad = dialogBinding.idSpinerVariedadAlerta
        val itemVariedades = arrayOf(
            "CASTILLO",
            "F6",
            "AMARILLO",
            "BORBON",
            "ROSADO",
            "VARIEDAD COLOMBIA",
            "CENICAFE I",
            "CATUEY",
            "GEISHA",
            "OTRO"
        )
        val arrayAdapterGenero = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemVariedades
        )
        spinerVariedad.adapter = arrayAdapterGenero
        variedad = spinerVariedad.selectedItem.toString()
        agregarLotes(numero_de_arboles, edad_del_lote, variedad)

        dialogBinding.idBtnLoteAlerta.setOnClickListener {
            if (validarFormularioLotes()) {
                listLotes.addAll(listOf(lotes))
                Constantes2.listaLotes = listLotes

                dialogBinding.idTxtNumeroArbolesAlerta.setText("")
                dialogBinding.idTxtEdadLoteAlerta.setText("")

                Log.e("Lotes", listLotes.toString())
                contLotes = contLotes + 1
                dialogBinding.idConteoRegistradosAlerta.text = contLotes.toString()
                if (contLotes == cantidadLotes.toInt()) {
                    alert_anadir.dismiss()
                    anadirOtroDatoAgricola()
                    contLotes = 0
                }
            }
        }
    }

    private fun agregarLotes(numLote: String, edadLote: String, variedadLote: String) {
        lotes = LotesProduccion(numLote, edadLote, variedadLote)
    }

    private fun validarFormularioLotes(): Boolean {
        instanciaFormularioLotes()
        var esValido = true
        if (TextUtils.isEmpty(numero_de_arboles)) {
            dialogBinding.idTxtNumeroArbolesAlerta.error = "Campo requerido"
            esValido = false
        } else {
            dialogBinding.idTxtNumeroArbolesAlerta.error = null
            esValido = true
        }
        if (TextUtils.isEmpty(edad_del_lote)) {
            dialogBinding.idTxtEdadLoteAlerta.error = "Campo requerido"
            esValido = false
        } else {
            dialogBinding.idTxtEdadLoteAlerta.error = null
            esValido = true
        }
        return esValido
    }
    */


    private fun initRecyclerView() {
        idRecyclerView = binding.idRecyclerrViewProductivos
        idRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        idRecyclerView.setHasFixedSize(true)

        myProductivosAdapter = ProduccionAgicolaAdapter(
            Constantes2.listaDatosFinca!!.datos_productivos as ArrayList<Productivos>,
            requireContext()
        )
        idRecyclerView.adapter = myProductivosAdapter

    }
}

