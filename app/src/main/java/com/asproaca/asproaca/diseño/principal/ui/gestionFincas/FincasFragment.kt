package com.asproaca.asproaca.diseño.principal.ui.gestionFincas

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.AsproacaNewAplication
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.FincasAdapter
import com.asproaca.asproaca.databinding.FragmentFincasBinding
import com.asproaca.asproaca.modelos.Finca
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*

class FincasFragment : Fragment(R.layout.fragment_fincas) {

    private lateinit var binding: FragmentFincasBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listaFincas: MutableList<Finca>
    private var _fincasArrayList = mutableListOf<Finca>()
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var myAdapter: FincasAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFincasBinding.bind(view)

        initRecyclerView()
        buscarFinca()
        primerIngreso()

        binding.idBtnAnadirFica.setOnClickListener {
            findNavController().navigate(R.id.action_nav_fincas_to_datosBasicosFragment)
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.idRecyclerViewFincas
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        listaFincas = arrayListOf()
        myAdapter = FincasAdapter(_fincasArrayList as ArrayList<Finca>, requireContext())
        recyclerView.adapter = myAdapter

        myAdapter.setOnClickListener {
            val position = binding.idRecyclerViewFincas.getChildAdapterPosition(it)
            val event = listaFincas[position]

        }
        eventChangeListener()
    }

    private fun eventChangeListener() {
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Fincas")
            .addSnapshotListener(
                MetadataChanges.INCLUDE,
                object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                try {
                                    listaFincas.add(dc.document.toObject(Finca::class.java))
                                } catch (e: Exception) {
                                    /**
                                     * Mandar exepcion en caso de error
                                     */
                                    Snackbar.make(
                                        binding.root,
                                        "Ha ocurrido un error, vuelva a intentarlo",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .setAction("Action", null).show()
                                }
                            }
                            myAdapter.notifyDataSetChanged()
                        }
                        _fincasArrayList.clear()
                        _fincasArrayList.addAll(listaFincas)
                    }
                })
    }

    private fun buscarFinca() {
        binding.idBuscarFinca.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarFinca(newText)
                return false
            }
        })
    }

    private fun filtrarFinca(text: String?) {
        val alternList = mutableListOf<Finca>()
        text?.let {
            if (it.isEmpty()) {
                _fincasArrayList.clear()
                _fincasArrayList.addAll(listaFincas)
                myAdapter.notifyDataSetChanged()
                return
            }

            for (modelApp in listaFincas) {
                if (modelApp.nombre_finca!!.lowercase()
                        .startsWith(it.lowercase()) || modelApp.nombre_finca!!.lowercase()
                        .startsWith(it.lowercase())
                ) {
                    alternList.add(modelApp)
                }
            }
            _fincasArrayList.apply {
                clear()
                addAll(alternList)
                myAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun mostrarTarjetas() {
        val tarjetas = mutableListOf<TapTarget>(
            TapTarget.forView(binding.idBuscarFinca, "Buscar finca por nombre").apply {
                tintTarget(false)
                dimColor(R.color.verdeAsproaca)
                textColor(R.color.white)
                transparentTarget(true)
                targetCircleColor(R.color.white)
                cancelable(false)
            },
            TapTarget.forView(binding.idBtnAnadirFica, "Añadir nueva finca").apply {
                tintTarget(false)
                dimColor(R.color.verdeAsproaca)
                textColor(R.color.white)
                transparentTarget(true)
                targetCircleColor(R.color.white)
                cancelable(false)
            }

        )
        TapTargetSequence(requireActivity()).targets(tarjetas).start()
    }

    private fun primerIngreso() {
        if (AsproacaNewAplication.preferencia.esPrimeraVezEnFincas(requireContext())) {
            mostrarTarjetas()
        }
    }


}