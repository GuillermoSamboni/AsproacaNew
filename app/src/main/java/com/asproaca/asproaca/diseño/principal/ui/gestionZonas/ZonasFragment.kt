package com.asproaca.asproaca.diseño.principal.ui.gestionZonas

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.ZonasAdapter
import com.asproaca.asproaca.databinding.FormularioZonasBinding
import com.asproaca.asproaca.databinding.FragmentZonasBinding
import com.asproaca.asproaca.modelos.ZonasModel
import com.google.firebase.firestore.*

class ZonasFragment : Fragment(R.layout.fragment_zonas) {
    private lateinit var binding: FragmentZonasBinding
    private lateinit var datBase: FirebaseFirestore
    private lateinit var listaZonas: MutableList<ZonasModel>

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: ZonasAdapter
    private lateinit var zonas: ZonasModel
    private lateinit var alert_anadir: AlertDialog

    private lateinit var dialogBinding: FormularioZonasBinding

    private lateinit var nombre_zona: String
    private lateinit var id_zona: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentZonasBinding.bind(view)
        binding.idBtnAnadirZonas.setOnClickListener {
            mostrarFormulario()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = binding.idRecyclerViewZonas
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        listaZonas = arrayListOf()
        myAdapter = ZonasAdapter(listaZonas as ArrayList<ZonasModel>, requireContext())
        recyclerView.adapter = myAdapter

        myAdapter.setOnClickListener {
            val position = binding.idRecyclerViewZonas.getChildAdapterPosition(it)
            val event = listaZonas[position]
            zonas = ZonasModel()
            zonas.nombre_zona = event.nombre_zona
        }

        listaZonas()
    }

    private fun listaZonas() {
        datBase = FirebaseFirestore.getInstance()
        datBase.collection("Zonas")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    } else {
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                try {
                                    listaZonas.add(dc.document.toObject(ZonasModel::class.java))
                                } catch (e: Exception) {
                                    //
                                }
                            }
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun mostrarFormulario() {
        dialogBinding = FormularioZonasBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()

        instanciaDatosFormulario()

        dialogBinding.idBtnRegistrarProveedor.setOnClickListener {
            instanciaDatosFormulario()
            if (validarFormulario()) {
                registrarProveedor(nombre_zona, nombre_zona)
            }
        }
        alert_anadir.show()
    }

    private fun registrarProveedor(idZona: String, nombre: String) {
        val datosZona = ZonasModel(idZona, nombre)

        datBase = FirebaseFirestore.getInstance()
        datBase.collection("Zonas").document(nombre_zona).set(datosZona).addOnCompleteListener {
            if (it.isSuccessful) {
                alert_anadir.dismiss()
            } else {
                //
            }
        }
    }

    private fun validarFormulario(): Boolean {
        var esValido = true
        if (TextUtils.isEmpty(dialogBinding.idTxtNombreZona.text.toString())) {
            esValido = false
            dialogBinding.idTxtNombreZona.error = "Campo requerido"
        } else {
            dialogBinding.idTxtNombreZona.error = null
        }
        return esValido
    }

    private fun instanciaDatosFormulario() {
        nombre_zona = dialogBinding.idTxtNombreZona.text.toString()
    }
}