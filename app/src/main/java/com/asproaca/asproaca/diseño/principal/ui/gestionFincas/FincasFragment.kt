package com.asproaca.asproaca.diseño.principal.ui.gestionFincas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.asproaca.asproaca.AsproacaNewAplication
import com.asproaca.asproaca.AsproacaNewAplication.Companion.preferencia
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.FincasAdapter
import com.asproaca.asproaca.databinding.FragmentFincasBinding
import com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosAmbientales.DatosAmbintalesViewModel
import com.asproaca.asproaca.modelos.Finca
import com.campo.campocolombiano.design.constantes.Constantes2
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FincasFragment : Fragment(R.layout.fragment_fincas) {

    private lateinit var binding: FragmentFincasBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listaFincas: MutableList<Finca>
    private var _fincasArrayList = mutableListOf<Finca>()
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var myAdapter: FincasAdapter
    private lateinit var viewModel: FincasViewModel

    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFincasBinding.bind(view)
        viewModel = ViewModelProvider(this)[FincasViewModel::class.java]


        initRecyclerView()
        buscarFinca()
        primerIngreso()


        binding.idBtnAnadirFica.setOnClickListener {
            findNavController().navigate(R.id.action_nav_fincas_to_datosBasicosFragment)
        }

        binding.idBtnCerrar.setOnClickListener {
            Firebase.auth.signOut()
            preferencia.borrarPreferencias()
        }

        if (preferencia.obtenerRol()
                .isNotEmpty() && preferencia.obtenerRol() == "Super Administrador"
        ) {
            val botonAgregar = view.findViewById<FloatingActionButton>(R.id.idBtnAnadirFica)
            botonAgregar.visibility = View.GONE
        } else {
            val botonAgregar = view.findViewById<FloatingActionButton>(R.id.idBtnAnadirFica)
            botonAgregar.visibility = View.VISIBLE
        }

        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout!!.setOnRefreshListener {
            swipeRefreshLayout!!.setRefreshing(true);
            if (recargarDatos()) {
                swipeRefreshLayout!!.setRefreshing(false);
            }
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

            val intent = Intent(context, ModificarFincaActivity::class.java)
            Constantes2.listaDatosFinca = event
            Constantes2.idFinca = event.idFinca
            startActivity(intent)
        }
        //obtenerFincas()
        eventChangeListener()
    }

    /**
    private fun obtenerFincas() {
    viewModel.clickResultadoFinca()
    viewModel.resultdoConsulta.observe(this@FincasFragment.requireActivity(),
    Observer { success ->
    if (success == true) {
    recargarDatos()
    } else {
    //
    }
    })
    }*/

    private fun eventChangeListener() {
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Fincas").document("Fincas").collection("ActualizacionFinca")
            .whereEqualTo("idUsuario", preferencia.obtenerIdUsuario())
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
                                    listaFincas.forEach {
                                        Constantes2.EstadoActualizar = it.estadoActualizar
                                    }
                                } catch (e: Exception) {
                                    Snackbar.make(
                                        binding.root,
                                        "Ha ocurrido un error, vuelva a intentarlo",
                                        Snackbar.LENGTH_LONG
                                    ).setAction("Action", null).show()
                                }
                            }
                            myAdapter.notifyDataSetChanged()
                        }
                        _fincasArrayList.clear()
                        _fincasArrayList.addAll(listaFincas)
                    }
                })
    }

    private fun recargarDatos(): Boolean {
        swipeRefreshLayout!!.setRefreshing(false);
        listaFincas = mutableListOf()
        eventChangeListener()
        return true

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