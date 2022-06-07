package com.asproaca.asproaca.diseño.autenticacion

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.PatternsCompat
import com.asproaca.asproaca.AsproacaNewAplication.Companion.preferencia
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.ActivityMainBinding
import com.asproaca.asproaca.databinding.ErrorIniciarsesionBinding
import com.asproaca.asproaca.diseño.principal.PrincipalActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var autenticacion: FirebaseAuth

    private lateinit var correoElectronico: String
    private lateinit var contrasena: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * Comprobar si ya inicio sesion
         */
        comprobarSesion()
        permisoUbicacion()

        autenticacion = FirebaseAuth.getInstance()

        binding.idBtnIngresar.setOnClickListener { it ->
            correoElectronico = binding.idTxtCorreoElectronico.text.toString()
            contrasena = binding.idTxtContrasena.text.toString()
            it.apply { iniciarSesion(correoElectronico, contrasena) }
        }

    }

    private fun comprobarSesion() { if (preferencia.obtenerUsuario().isNotEmpty()) { pasarPantalla() } }

    private fun iniciarSesion(correo: String, contrasena: String) {
        if (validarFormulario()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { respuesta ->
                    if (respuesta.isSuccessful) { if (binding.idTxtCheckBox.isChecked) { preferencia.guardarUsuario(correo) }
                        pasarPantalla() }
                }.addOnFailureListener { mostrarError() }
        }
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
            if (!PatternsCompat.EMAIL_ADDRESS.matcher(binding.idTxtCorreoElectronico.text.toString()).matches()) {
                binding.idTxtCorreoElectronico.error = "Correo no valido"
                esValido = false

            } else { binding.idTxtCorreoElectronico.error = null }
        }

        if (TextUtils.isEmpty(contrasena)) {
            binding.idTxtContrasena.error = "Campo requerido"
            esValido = false
        } else { binding.idTxtContrasena.error = null }

        return esValido
    }

    private fun mostrarError() {
        val alertaError = ErrorIniciarsesionBinding.inflate(LayoutInflater.from(this))
        val alertDialog = AlertDialog.Builder(this).apply {
            setView(alertaError.root)
            setCancelable(false)
        }.create()
        //alertDialog.window?.setBackgroundDrawableResource(R.color.transparente)
        alertaError.idBtnAceptarError.setOnClickListener { alertDialog.dismiss()
            alertDialog.setCancelable(true) }
        alertDialog.show()
    }

    private fun permisoUbicacion() {
        val permissionubication =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionubication == PackageManager.PERMISSION_DENIED) { ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else { ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1) }
    }
}