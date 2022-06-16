package com.asproaca.asproaca.diseño.principal.ui.perfil

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentModificarDatosBinding
import com.asproaca.asproaca.modelos.Usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

var conteo = 0
var superAdmin = false
var adminNormal = false

class ModificarDatosFragment : Fragment(R.layout.fragment_modificar_datos) {
    private lateinit var binding: FragmentModificarDatosBinding
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var uid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentModificarDatosBinding.bind(view)
        obtenerInfomacionUsuario()
        modificarDatos()


    }

    private fun obtenerInfomacionUsuario() {
        val user = Firebase.auth.currentUser
        user?.let {
            name = user.displayName.toString()
            email = user.email.toString()
            uid = user.uid.toString()
        }
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Usuarios").document(uid).get().addOnSuccessListener { document ->
            if (document != null) {
                val userData = document.toObject<Usuario>()
                binding.idModificarNombre.setText(userData?.nombre)
                binding.idModificarApellido.setText(userData?.apellido)
                binding.idModificarIdentificacion.setText(userData?.identificacion)
                binding.idModificarTelefono.setText(userData?.telefono)
                binding.idModificarCorreo.setText(userData?.correo)
                binding.idModificarRol.setText(userData?.rol)
                binding.idModificarContrasena.setText(userData?.contrasena)
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun modificarDatos() {
        binding.idBtnModificar.setOnClickListener {
            dataBase.collection("Usuarios").document(uid).update(
                mapOf(
                    "nombre" to binding.idModificarNombre.text.toString(),
                    "apellido" to binding.idModificarApellido.text.toString(),
                    "identificacion" to binding.idModificarIdentificacion.text.toString(),
                    "correo" to binding.idModificarCorreo.text.toString(),
                    "rol" to binding.idModificarRol.text.toString(),
                    "telefono" to binding.idModificarTelefono.text.toString(),
                    "contrasena" to binding.idModificarContrasena.text.toString()
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mostrarPersonalizado()
                } else {

                }
            }
        }
    }

    private fun mostrarPersonalizado() {
        val toast = Toast(requireContext())
        val mview = LayoutInflater.from(requireContext())
            .inflate(R.layout.custom_toast_modificacion_exitosa, null, false)
        toast.setView(mview)
        toast.setGravity(Gravity.TOP, 30, 30)
        toast.show()
    }


}