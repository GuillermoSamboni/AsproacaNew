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
import androidx.navigation.fragment.findNavController
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertaAnimalesBinding
import com.asproaca.asproaca.databinding.FragmentAnimalesBinding
import com.asproaca.asproaca.modelos.Animales
import com.campo.campocolombiano.design.constantes.Constantes2

class AnimalesModificarFragment : Fragment(R.layout.fragment_animales) {
    private  lateinit var binding :FragmentAnimalesBinding

    private lateinit var nombre_animal: String
    private lateinit var cantidad_animal: String
    private lateinit var area_crianza_animal: String

    private lateinit var alert_anadir: AlertDialog
    private lateinit var dialogBinding: AlertaAnimalesBinding

    private lateinit var animales: Animales
    private val listaAnimales = mutableListOf<Animales>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimalesBinding.bind(view)

        instanciaDatosFormulario()
        ponerDatos()
        binding.idBtnContinuarProceso.setOnClickListener {
            agregarDatos()
        }
    }

    private fun ponerDatos() {
        Constantes2.listaDatosFinca!!.datos_animal!!.forEach {
            //nombre_animal = binding.iTXT
            cantidad_animal = binding.idTxtNumeroAnimalesFinca.setText(it.cantidad_animal).toString()
            area_crianza_animal = binding.idTxtAreaTotalAnimalesFinca.setText(it.area_crianza_animal).toString()
        }
    }

    private fun instanciaDatosFormulario() {

        val spineraaAnimales = binding.idSpinerAnimalesFinca
        val itemsAnimales = arrayOf(
            "VACUNO",
            "PORCICOLA",
            "CUYES",
            "CUNICULTURA",
            "GALLINAS DE ENGORDE",
            "GALLINAS PONEDORAS",
            "PISCICULTURA",
            "OTRO"
        )

        val arrayAdapterAnimales = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsAnimales
        )
        spineraaAnimales.adapter = arrayAdapterAnimales
        nombre_animal = spineraaAnimales.selectedItem.toString()

        cantidad_animal = binding.idTxtNumeroAnimalesFinca.text.toString()
        area_crianza_animal = binding.idTxtAreaTotalAnimalesFinca.text.toString()
    }

    private fun validarDatosFormulario(): Boolean {
        var esValido = true
        if (TextUtils.isEmpty(cantidad_animal)) {
            esValido = false
            binding.idTxtNumeroAnimalesFinca.error = "Campo requerido"
        } else {
            binding.idTxtNumeroAnimalesFinca.error = null
        }
        if (TextUtils.isEmpty(area_crianza_animal)) {
            esValido = false
            binding.idTxtAreaTotalAnimalesFinca.error = "Campo requerido"
        } else {
            binding.idTxtAreaTotalAnimalesFinca.error = null
        }

        return esValido
    }

    private fun agregarDatos() {
        instanciaDatosFormulario()
        if (validarDatosFormulario()) {
            animales = Animales(nombre_animal, cantidad_animal, area_crianza_animal)
            listaAnimales.addAll(listOf(animales))
            Constantes2.listaAnimales = listaAnimales
            //Log.e("ListaAnimales", Constantes2.listaAnimales.toString())
            mostrarAlertaConfimar()
            limpiarFormulario()
        }
    }

    private fun agregarDatosAlerta() {
        instanciaFormularioalerta()
        animales = Animales(nombre_animal, cantidad_animal, area_crianza_animal)

        instanciaFormularioalerta()

        if (validarDatosFormulariAlerta()) {
            animales = Animales(nombre_animal, cantidad_animal, area_crianza_animal)
            listaAnimales.addAll(listOf(animales))
            Constantes2.listaAnimales = listaAnimales
            Log.e("ListaAnimales", Constantes2.listaAnimales.toString())

            mostrarAlertaConfimar()
            alert_anadir.dismiss()
            //limpiarFormulario()
        }

    }

    private fun instanciaFormularioalerta() {
        val spineraaAnimales = dialogBinding.idSpinerAnimalesFincaA
        val itemsAnimales = arrayOf(
            "VACUNO",
            "PORCICOLA",
            "CUYES",
            "CUNICULTURA",
            "GALLINAS DE ENGORDE",
            "GALLINAS PONEDORAS",
            "PISCICULTURA",
            "OTRO"
        )

        val arrayAdapterAnimales = ArrayAdapter(
            requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
            itemsAnimales
        )
        spineraaAnimales.adapter = arrayAdapterAnimales
        nombre_animal = spineraaAnimales.selectedItem.toString()

        cantidad_animal = dialogBinding.idTxtNumeroAnimalesFincaA.text.toString()
        area_crianza_animal = dialogBinding.idTxtAreaTotalAnimalesFincaA.text.toString()
    }

    private fun limpiarFormulario() {
        binding.idTxtNumeroAnimalesFinca.setText("").toString()
        binding.idTxtAreaTotalAnimalesFinca.setText("").toString()
    }

    private fun mostrarAlertaConfimar() {
        val alertaConfirmar = AlertDialog.Builder(requireContext())
        alertaConfirmar.setTitle("Agregar Animal")
        alertaConfirmar.setMessage("¿Desea agregar un animal más?")
        alertaConfirmar.setCancelable(false)
        alertaConfirmar.setPositiveButton("Agregar") { _, _ ->
            alertaConfirmar.setCancelable(true)
            mostrarFromulario()
        }.setNegativeButton("Continuar proceso") { _, _ ->
            alertaConfirmar.setCancelable(true)
            findNavController().navigate(R.id.action_animalesFragment_to_trabajadoresFragment)
        }
        alertaConfirmar.show()
    }

    private fun mostrarFromulario() {
        dialogBinding = AlertaAnimalesBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()

        instanciaFormularioalerta()
        animales = Animales(nombre_animal, cantidad_animal, area_crianza_animal)
        instanciaFormularioalerta()

        dialogBinding.idBtnContinuarProcesoA.setOnClickListener {

            agregarDatosAlerta()
        }

        alert_anadir.setCancelable(false)
        alert_anadir.show()
    }

    private fun validarDatosFormulariAlerta(): Boolean {
        var esValido = true
        if (TextUtils.isEmpty(cantidad_animal)) {
            esValido = false
            dialogBinding.idTxtNumeroAnimalesFincaA.error = "Campo requerido"
        } else {
            dialogBinding.idTxtNumeroAnimalesFincaA.error = null
        }
        if (TextUtils.isEmpty(area_crianza_animal)) {
            esValido = false
            dialogBinding.idTxtAreaTotalAnimalesFincaA.error = "Campo requerido"
        } else {
            dialogBinding.idTxtAreaTotalAnimalesFincaA.error = null
        }

        return esValido
    }


}