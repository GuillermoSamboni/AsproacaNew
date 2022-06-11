package com.asproaca.asproaca.diseño.principal.ui.gestionUsuarios

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.CustomCargaBinding
import com.asproaca.asproaca.databinding.FragmentRegistrarUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrarUsuarioFragment : Fragment(R.layout.fragment_registrar_usuario) {
    private lateinit var binding: FragmentRegistrarUsuarioBinding
    private lateinit var nombreInput: String
    private lateinit var apellidoInput: String
    private lateinit var identificacionUserInput: String
    private lateinit var telefonoInput: String


    private var rolInput: String = "nulo"
    private lateinit var constrasenaInput: String
    private lateinit var alertCarga: AlertDialog
    private lateinit var correo: String
    private var dataBase = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrarUsuarioBinding.bind(view)

        animacionDeCarga()
        auth = FirebaseAuth.getInstance()
        binding.idBtnRegister
            .setOnClickListener {
                registerUserFirestore()
            }
    }

    private fun registerUserFirestore() {
        nombreInput = binding.idInputName.text.toString()
        apellidoInput = binding.idInputLastName.text.toString()
        identificacionUserInput = binding.idInputIdentification.text.toString()
        telefonoInput = binding.idInputPhone.text.toString()
        constrasenaInput = binding.idInputPass.text.toString()
        correo = binding.idInputEmail.text.toString()

//        rolInput = binding.idTxtAuCompletRole.text.toString()
        val radoGroup = binding.idLayoutPerfil
        radoGroup.setOnCheckedChangeListener { grpup, checkedid ->
            when (checkedid) {
                R.id.idSuperAdmin -> {
                    rolInput = "Super Administrador"
                }
                R.id.idAdmin -> {
                    rolInput = "Administrador"
                }
            }
        }

        if (validarFormulario()) {
            if (rolInput == "nulo") {
                binding.idPerfilError.visibility = View.VISIBLE
            } else {
                if (constrasenaInput.length > 6) {
                    alertCarga.show()
                    auth.createUserWithEmailAndPassword(correo, constrasenaInput)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userData = hashMapOf(
                                    "idUsuario" to auth.uid,
                                    "nombre" to nombreInput,
                                    "apellido" to apellidoInput,
                                    "identificacion" to identificacionUserInput,
                                    "telefono" to telefonoInput,
                                    "rol" to rolInput,
                                    "contrasena" to constrasenaInput,
                                    "correo" to correo,
                                )
                                dataBase.collection("Usuarios").document(auth.uid.toString())
                                    .set(userData)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            alertCarga.dismiss()
                                            Toast.makeText(
                                                requireContext(),
                                                "Usuario creado exitosamente",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.idInputName.setText("")
                                            binding.idInputLastName.setText("")
                                            binding.idInputIdentification.setText("")
                                            binding.idInputPhone.setText("")
                                            binding.idInputPass.setText("")
//                                            binding.idTxtAuCompletRole.setText("")
                                            binding.idInputEmail.setText("")
                                        } else {
                                            alertCarga.dismiss()
                                            Toast.makeText(
                                                requireContext(),
                                                "Usuario no creado",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                alertCarga.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    "No se ha creado el usuario",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    alertCarga.dismiss()
                    binding.idInputPass.error = "La contraseña debe ser mayor a 6 caracteres"
                }
            }


        }
    }

    private fun validarFormulario(): Boolean {
        var nextAction = true
        //Nombre
        if (nombreInput.isEmpty()) {
            binding.idLayoutNombre.setError("Campo obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //Apellido
        if (apellidoInput.isEmpty()) {
            binding.idLayoutApellido.setError("Campo obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //Identificación
        if (identificacionUserInput.isEmpty()) {
            binding.idLayoutIdentificacion.setError("Campo obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //telefono
        if (telefonoInput.isEmpty()) {
            binding.idLayoutTelefono.setError("Campo obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //telefono
        if (telefonoInput.isEmpty()) {
            binding.idLayoutTelefono.setError("Campo obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //Correo
        if (binding.idInputEmail.text.toString().isEmpty()) {
            binding.idLayoutCorreo.setError("Campo Obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        //Contraseña
        if (constrasenaInput.isEmpty()) {
            binding.idLayoutContrasena.setError("Campo Obligatorio")
            nextAction = false
        } else {
            nextAction = true
        }
        return nextAction
    }

    private fun animacionDeCarga() {
        val dialogBinding = CustomCargaBinding.inflate(LayoutInflater.from(context))

        alertCarga = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        alertCarga.setCancelable(false)
        alertCarga.window?.setBackgroundDrawableResource(R.color.color_transparent)
    }
}