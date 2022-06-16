package com.asproaca.asproaca.diseño.principal.ui.autenticacion

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.PatternsCompat
import com.asproaca.asproaca.AsproacaNewAplication.Companion.preferencia
import com.asproaca.asproaca.databinding.ActivityMainBinding
import com.asproaca.asproaca.databinding.ErrorIniciarsesionBinding
import com.asproaca.asproaca.diseño.principal.PrincipalActivity
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var autenticacion: FirebaseAuth

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var uid: String
    private lateinit var dataBase: FirebaseFirestore

    private lateinit var correoElectronico: String
    private lateinit var contrasena: String
    private lateinit var idUsuario: String
    private var rolePassContinue: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * Comprobar si ya inicio sesion
         */
        permisoUbicacion()
        comprobarSesion()

        autenticacion = FirebaseAuth.getInstance()

        binding.idBtnIngresar.setOnClickListener { it ->
            correoElectronico = binding.idTxtCorreoElectronico.text.toString()
            contrasena = binding.idTxtContrasena.text.toString()
            it.apply { iniciarSesion(correoElectronico, contrasena) }
        }

    }

    private fun comprobarSesion() {
        if (preferencia.obtenerUsuario().isNotEmpty()) {
            pasarPantalla()
        }
    }

    private fun iniciarSesion(correo: String, contrasena: String) {
        if (validarFormulario()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { respuesta ->
                    if (respuesta.isSuccessful) {
                        if (binding.idTxtCheckBox.isChecked) {
                            preferencia.guardarUsuario(correo)
                            if (preferencia.guardarIdUsuario(respuesta.result.user!!.uid)) {
                                if (obtenerInfomacionUsuario()) {
                                    pasarPantalla()
                                }
                            }
                        } else {
                            if (preferencia.guardarIdUsuario(respuesta.result.user!!.uid)) {
                                if (obtenerInfomacionUsuario()) {
                                    pasarPantalla()
                                }
                            }
                        }
                    }
                }.addOnFailureListener { mostrarError() }
        }
    }

    private fun obtenerInfomacionUsuario(): Boolean {
        var seObtuvo = true
        val user = Firebase.auth.currentUser
        user?.let {
            name = user.displayName.toString()
            email = user.email.toString()
            uid = user.uid
        }
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Usuarios").document(uid).get().addOnSuccessListener { document ->
            if (document != null) {
                if (preferencia.guardarRol(document.data?.get("rol").toString())) {
                    Constantes2.idUsuario = uid
                    Constantes2.encargadoRegistro = document.data?.get("rol").toString()
                    rolePassContinue = document.data?.get("rol").toString()
                    seObtuvo = true
                } else {
                    seObtuvo = false
                }
            }
        }
        return seObtuvo
    }

    private fun pasarPantalla() {
        val intentIniciarSesion = Intent(this, PrincipalActivity::class.java)
        startActivity(intentIniciarSesion)
        finish()
    }

    private fun validarFormulario(): Boolean {
        var esValido = true

        if (TextUtils.isEmpty(correoElectronico)) {
            binding.idTxtCorreoElectronico.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtCorreoElectronico.error = null
            if (!PatternsCompat.EMAIL_ADDRESS.matcher(binding.idTxtCorreoElectronico.text.toString())
                    .matches()
            ) {
                binding.idTxtCorreoElectronico.error = "Correo no valido"
                esValido = false

            } else {
                binding.idTxtCorreoElectronico.error = null
            }
        }

        if (TextUtils.isEmpty(contrasena)) {
            binding.idTxtContrasena.error = "Campo requerido"
            esValido = false
        } else {
            binding.idTxtContrasena.error = null
        }

        return esValido
    }

    private fun mostrarError() {
        val alertaError = ErrorIniciarsesionBinding.inflate(LayoutInflater.from(this))
        val alertDialog = AlertDialog.Builder(this).apply {
            setView(alertaError.root)
            setCancelable(false)
        }.create()
        //alertDialog.window?.setBackgroundDrawableResource(R.color.transparente)
        alertaError.idBtnAceptarError.setOnClickListener {
            alertDialog.dismiss()
            alertDialog.setCancelable(true)
        }
        alertDialog.show()
    }

    private fun permisoUbicacion() {
        val permissionubication =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionubication == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }
}