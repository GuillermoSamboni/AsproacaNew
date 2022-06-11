package com.asproaca.asproaca.diseño.principal.ui.gestionUsuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.AsproacaNewAplication
import com.asproaca.asproaca.R
import com.asproaca.asproaca.adaptadores.UsuariosAdapter
import com.asproaca.asproaca.databinding.FragmentUsuariosBinding
import com.asproaca.asproaca.modelos.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosFragment : Fragment(R.layout.fragment_usuarios) {

    private lateinit var binding: FragmentUsuariosBinding

    private lateinit var dataBase: FirebaseFirestore
    private lateinit var usuariosArray: MutableList<Usuario>
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: UsuariosAdapter
    private lateinit var usuario: Usuario

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsuariosBinding.bind(view)

        binding.idBtnAnadirUsuario.setOnClickListener {
            findNavController().navigate(R.id.action_nav_usuarios_to_registrarUsuarioFragment)
        }
        if (AsproacaNewAplication.preferencia.obtenerRol()
                .isNotEmpty() && AsproacaNewAplication.preferencia.obtenerRol() == "Super Administrador"
        ) {
            val botonAgregar = view.findViewById<FloatingActionButton>(R.id.idBtnAnadirUsuario)
            botonAgregar.visibility = View.VISIBLE
        } else {
            val botonAgregar = view.findViewById<FloatingActionButton>(R.id.idBtnAnadirUsuario)
            botonAgregar.visibility = View.GONE
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = binding.idRecyclerViewUsuario
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        usuariosArray = arrayListOf()
        myAdapter = UsuariosAdapter(usuariosArray as ArrayList<Usuario>, requireContext())
        recyclerView.adapter = myAdapter

        myAdapter.setOnClickListener {
            val position = binding.idRecyclerViewUsuario.getChildAdapterPosition(it)
            val event = usuariosArray[position]
            usuario = Usuario()
            usuario.nombre = event.nombre
            usuario.apellido = event.apellido
            usuario.identificacion = event.identificacion
            usuario.telefono = event.telefono
            usuario.apellido = event.apellido
            usuario.correo = event.correo
            usuario.contrasena = event.contrasena
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                obtenerUsuarios()
            }
        }
    }

    private fun obtenerUsuarios() {
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Usuarios").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                                usuariosArray.add(dc.document.toObject(Usuario::class.java))
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