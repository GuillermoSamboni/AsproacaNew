package com.asproaca.asproaca.diseño.principal.ui.perfil

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.CustomCargaBinding
import com.asproaca.asproaca.databinding.FragmentPerfilBinding
import com.asproaca.asproaca.modelos.Usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PerfilFragment : Fragment(R.layout.fragment_perfil) {
    private lateinit var binding: FragmentPerfilBinding

    private lateinit var email: String
    private lateinit var uid: String
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var alertCarga: AlertDialog
    var conteo = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)
        animacionDeCarga()
        obtenerInfomacionPersona()
        saltarAModificarDatos()

        binding.idBtnActivar.setOnClickListener {
            activarModificaciones()
        }
        binding.idBtnDesacticar.setOnClickListener {
            desactivarModificaciones()
        }
    }

    private fun obtenerInfomacionPersona() {
        alertCarga.show()
        val user = Firebase.auth.currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Usuarios").document(uid).get().addOnSuccessListener { document ->
            if (document != null) {
                val datosPersona = document.toObject<Usuario>()
                binding.idNombre.setText(datosPersona?.nombre)
                binding.idpellido.setText(datosPersona?.apellido)
                binding.idIdentificacion.setText(datosPersona?.identificacion)
                binding.idCorreo.setText(datosPersona?.correo)
                binding.idTelefono.setText(datosPersona?.telefono)
                alertCarga.dismiss()
            } else {
                alertCarga.dismiss()
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            alertCarga.dismiss()
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saltarAModificarDatos() {
        //binding.idBtnFloatEditar.setOnClickListener {
        //findNavController().navigate(R.id.action_navigation_notifications_to_modificarDatosFragment)
        // }
    }

    private fun animacionDeCarga() {
        val dialogBinding = CustomCargaBinding.inflate(LayoutInflater.from(context))

        alertCarga = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        alertCarga.setCancelable(false)
        alertCarga.window?.setBackgroundDrawableResource(R.color.color_transparent)
    }

    private fun activarModificaciones() {

        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Fincas").document("Fincas").collection("ActualizacionFinca")
            .whereEqualTo("estadoActualizar", false)
            .get()
            .addOnSuccessListener {
                for (i in it) {
                    dataBase = FirebaseFirestore.getInstance()
                    dataBase.collection("Fincas").document("Fincas")
                        .collection("ActualizacionFinca")
                        .document(i.id)
                        .update("estadoActualizar", true).addOnCompleteListener {
                            conteo += 1
                            if (it.isComplete) {
                                if (conteo == 1) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Estados listos para actualizar",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
    }

    private fun desactivarModificaciones() {
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Fincas").document("Fincas").collection("ActualizacionFinca")
            .whereEqualTo("estadoActualizar", true)
            .get()
            .addOnSuccessListener {
                for (i in it) {
                    dataBase = FirebaseFirestore.getInstance()
                    dataBase.collection("Fincas").document("Fincas")
                        .collection("ActualizacionFinca")
                        .document(i.id)
                        .update("estadoActualizar", false).addOnCompleteListener {
                            conteo += 1
                            if (it.isComplete) {
                                if (conteo == 1) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Estados listos para actualizar",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
    }

}