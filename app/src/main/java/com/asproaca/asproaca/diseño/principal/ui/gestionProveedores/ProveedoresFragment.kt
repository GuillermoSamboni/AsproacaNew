package com.asproaca.asproaca.diseño.principal.ui.gestionProveedores

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.ProveedoresAdapter
import com.asproaca.asproaca.databinding.FormularioProveedorBinding
import com.asproaca.asproaca.databinding.FragmentProveedoresBinding
import com.asproaca.asproaca.modelos.Proveedor
import com.google.firebase.firestore.*

class ProveedoresFragment : Fragment(R.layout.fragment_proveedores) {

    private lateinit var binding: FragmentProveedoresBinding
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var listaProveedores: MutableList<Proveedor>
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: ProveedoresAdapter
    private lateinit var proveedor: Proveedor
    private lateinit var alert_anadir: AlertDialog

    private lateinit var dialogBinding: FormularioProveedorBinding

    private lateinit var nombre: String
    private lateinit var nit: String
    private lateinit var telefono: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProveedoresBinding.bind(view)

        initRecyclerView()

        binding.idBtnAnadirProveedor.setOnClickListener {
            mostrarFormulario()
        }
    }
    private fun mostrarFormulario() {
        dialogBinding = FormularioProveedorBinding.inflate(LayoutInflater.from(context))
        alert_anadir = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        alert_anadir.setCancelable(true)
        instanciaDatosFormulario()

        dialogBinding.idBtnRegistrarProveedor.setOnClickListener {
            instanciaDatosFormulario()
            if (validarFormulario()) {
                registrarProveedor(nombre, nit, telefono)
            }
        }
        alert_anadir.show()
    }

    private fun instanciaDatosFormulario() {
        nombre = dialogBinding.idTxtNombreProveedor.text.toString()
        nit = dialogBinding.idTxtNitProveedor.text.toString()
        telefono = dialogBinding.idTxtTelefonoProvedor.text.toString()

    }

    private fun validarFormulario(): Boolean {
        var esValido = true
        if (TextUtils.isEmpty(dialogBinding.idTxtNombreProveedor.text.toString())) {
            esValido = false
            dialogBinding.idTxtNombreProveedor.error = "Campo requerdo"
        } else {
            dialogBinding.idTxtNombreProveedor.error = null
        }
        if (TextUtils.isEmpty(dialogBinding.idTxtNitProveedor.text.toString())) {
            esValido = false
            dialogBinding.idTxtNitProveedor.error = "Campo requerido"
        } else {
            dialogBinding.idTxtNitProveedor.error = null
        }
        if (TextUtils.isEmpty(dialogBinding.idTxtTelefonoProvedor.text.toString())) {
            esValido = false
            dialogBinding.idTxtTelefonoProvedor.error = "Campo requerido"
        } else {
            dialogBinding.idTxtTelefonoProvedor.error = null
        }
        return esValido
    }

    private fun registrarProveedor(nombre: String, nit: String, telefono: String) {
        val dataProveedor = hashMapOf(
            "id_proveedor" to nombre,
            "nombre_proveedor" to nombre,
            "nit_proveedor" to nit,
            "telefono_roveedor" to telefono
        )
        dataBase.collection("Proveedores").document(nombre).set(dataProveedor).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Proveedor Creado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                alert_anadir.dismiss()
            } else {
                Toast.makeText(requireContext(), "Proveedor no registrado", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "error=> ${it.message.toString()}", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.idRecyclerViewProveedor
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        listaProveedores = arrayListOf()
        myAdapter = ProveedoresAdapter(listaProveedores as ArrayList<Proveedor>, requireContext())
        recyclerView.adapter = myAdapter

        myAdapter.setOnClickListener {
            val position = binding.idRecyclerViewProveedor.getChildAdapterPosition(it)
            val event = listaProveedores[position]
            proveedor = Proveedor()
            proveedor.nombre_proveedor = event.nombre_proveedor
            proveedor.nit_proveedor = event.nit_proveedor
            proveedor.telefono_roveedor = event.telefono_roveedor
        }

        obtenerProveedores()
    }

    private fun obtenerProveedores() {
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Proveedores")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Toast.makeText(
                            requireContext(),
                            "Error => ${error.message.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    } else {
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                try {
                                    listaProveedores.add(dc.document.toObject(Proveedor::class.java))
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        requireContext(),
                                        e.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            })
    }

}