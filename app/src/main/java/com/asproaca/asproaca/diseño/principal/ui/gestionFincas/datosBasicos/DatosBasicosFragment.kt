package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosBasicos

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentDatosBasicosBinding
import com.asproaca.asproaca.modelos.Municipios
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class DatosBasicosFragment : Fragment(R.layout.fragment_datos_basicos) {
    private lateinit var binding: FragmentDatosBasicosBinding
    private lateinit var nombre_finca: String
    private lateinit var coordenada_x: String
    private lateinit var coordenada_y: String
    private var departamento: String? = null
    private var nombre: String? = null
    private lateinit var municipio: String

    private lateinit var vereda_finca: String
    private lateinit var zona: String
    private lateinit var antiguedad_finca: String
    private lateinit var historia_finca: String
    private var realiza_quema: String = ""
    private lateinit var creado: String
    private lateinit var certificaciones: String
    private lateinit var cantidad_viviendas: String
    private var zona_riesgo: String = ""
    private var tenencia_de_la_tierra: String = ""
    private lateinit var area_total: String
    private lateinit var dateInString: String
    private lateinit var listaMunicipios: MutableList<Municipios>
    private var itemMunicipios = mutableListOf<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDatosBasicosBinding.bind(view)

        instanciaDatosFormulario()

        coordenada_x = binding.idTxtCordenadaX.setText(Constantes2.coordenada_x).toString()
        coordenada_y = binding.idTxtCordenadaY.setText(Constantes2.coordenada_y).toString()


        val date = getCurrentDateTime()
        dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

        binding.idBtnContinuar.setOnClickListener {
            pasarDatosPaso1()
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun pasarDatosPaso1() {
        instanciaDatosFormulario()
        if (validarFormulario()) {
            Constantes2.nombre_finca = nombre_finca
            Constantes2.coordenada_x = coordenada_x
            Constantes2.coordenada_y = coordenada_y
            Constantes2.vereda_finca = vereda_finca
            Constantes2.zona = zona
            Constantes2.antiguedad_finca = antiguedad_finca
            Constantes2.historia_finca = historia_finca
            Constantes2.realiza_quema = realiza_quema
            Constantes2.creado = dateInString
            Constantes2.certificaciones = certificaciones
            Constantes2.cantidad_viviendas = cantidad_viviendas
            Constantes2.zona_riesgo = zona_riesgo
            Constantes2.tenencia_de_la_tierra = tenencia_de_la_tierra
            Constantes2.area_total = area_total

            findNavController().navigate(R.id.action_datosBasicosFragment_to_casaFragment)

        }

    }

    private fun instanciaDatosFormulario() {
        nombre_finca = binding.idTxtNombreFinca.text.toString()

        val spinerMunicipios = binding.idSpinerMunicipio
        itemMunicipios = itemMunicipios

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemMunicipios
        )
        spinerMunicipios.adapter = arrayAdapter
        try {
            municipio = spinerMunicipios.selectedItem.toString()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_LONG).show()
        }

        vereda_finca = binding.idTxtVeredaFinca.text.toString()
        zona = binding.idTxtZonaFinca.text.toString()
        antiguedad_finca = binding.idAntiguedadFinca.text.toString()
        historia_finca = binding.idHistoriaFinca.text.toString()

        val radioGruupQuema = binding.idRadioGroupQuema
        radioGruupQuema.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.idSiQuema -> {
                    realiza_quema = "Si"
                }
                R.id.idNoQuema -> {
                    realiza_quema = "No"
                }
            }
        }

        certificaciones = binding.idTxtCertificaciones.text.toString()
        cantidad_viviendas = binding.idTxtCantidadViviendas.text.toString()

        val radioGroupZonaRiesgo = binding.idRadioGroupRiesgo
        radioGroupZonaRiesgo.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.idSiRiesgo -> {
                    zona_riesgo = "Si"
                }
                R.id.idNoRiesgo -> {
                    zona_riesgo = "No"
                }
            }
        }

        val radioGroupTenencia = binding.idRadioGroupTenencia
        radioGroupTenencia.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.idRadioEscritura -> {
                    tenencia_de_la_tierra = "Escritura"
                }
                R.id.idRadioSanaPosesion -> {
                    tenencia_de_la_tierra = "Sana Posesión"
                }
                R.id.idRadioOtraPosecion -> {
                    tenencia_de_la_tierra = "Otro"
                }
            }
        }
        area_total = binding.idTxtAreaTotal.text.toString()

        obtenerMunicipios()

    }

    private fun validarFormulario(): Boolean {
        var nextAction = true
        if (TextUtils.isEmpty(nombre_finca)) {
            nextAction = false
            binding.idTxtNombreFinca.error = "Campo requerido"
        } else binding.idTxtNombreFinca.error = null

        if (TextUtils.isEmpty(vereda_finca)) {
            nextAction = false
            binding.idTxtVeredaFinca.error = "Campo requerido"
        } else binding.idTxtVeredaFinca.error = null

        if (TextUtils.isEmpty(zona)) {
            binding.idTxtZonaFinca.error = "Campo requerido"
            nextAction = false
        } else binding.idTxtZonaFinca.error = null

        if (TextUtils.isEmpty(antiguedad_finca)) {
            nextAction = false
            binding.idAntiguedadFinca.error = "Campo requerido"
        } else binding.idAntiguedadFinca.error = null

        if (TextUtils.isEmpty(historia_finca)) {
            binding.idHistoriaFinca.error = "Campo Requerido"
            nextAction = false
        } else binding.idHistoriaFinca.error = null

        if (!(binding.idSiQuema.isChecked || binding.idNoQuema.isChecked)) {
            nextAction = false
            binding.idErrorQuema.visibility = View.VISIBLE

        } else {
            binding.idErrorQuema.visibility = View.GONE
        }

        if (TextUtils.isEmpty(certificaciones)) {
            binding.idTxtCertificaciones.error = "Campo requerido"
            nextAction = false
        } else binding.idTxtCertificaciones.error = null

        if (TextUtils.isEmpty(cantidad_viviendas)) {
            nextAction = false
            binding.idTxtCantidadViviendas.error = "Campo requerido"
        } else binding.idTxtCantidadViviendas.error = null

        if (!(binding.idSiRiesgo.isChecked || binding.idNoRiesgo.isChecked)) {
            nextAction = false
            binding.idErrorRiesgo.visibility = View.VISIBLE

        } else {
            binding.idErrorRiesgo.visibility = View.GONE
        }

        if (!(binding.idRadioEscritura.isChecked || binding.idRadioSanaPosesion.isChecked || binding.idRadioOtraPosecion.isChecked)) {
            nextAction = false
            binding.idErrorTenencia.visibility = View.VISIBLE
        } else {
            binding.idErrorTenencia.visibility = View.GONE

        }

        if (TextUtils.isEmpty(area_total)) {
            nextAction = false
            binding.idTxtAreaTotal.error = "Campo requerido"
        } else binding.idTxtAreaTotal.error = null

        return nextAction
    }


    private fun obtenerMunicipios() {
        Firebase.firestore.collection("Municipios").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                listaMunicipios = task.result.toObjects(Municipios::class.java)
                listaMunicipios.forEach {
                    val muni = Municipios(
                        it.depto,
                        it.nombre
                    )
                    itemMunicipios.addAll(listOf(muni.nombre.toString()))
                }
                //itemMunicipios.addAll(listOf(listaMunicipios.toString()))
            }
        }.addOnFailureListener {
            //
        }
    }
}