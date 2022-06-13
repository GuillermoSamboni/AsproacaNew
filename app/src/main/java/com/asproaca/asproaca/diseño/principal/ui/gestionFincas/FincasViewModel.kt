package com.asproaca.asproaca.diseño.principal.ui.gestionFincas

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asproaca.asproaca.AsproacaNewAplication
import com.asproaca.asproaca.adaptadores.FincasAdapter
import com.asproaca.asproaca.modelos.Finca
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FincasViewModel : ViewModel() {
    private var dataBase = Firebase.firestore
    private val _rsultRegister = MutableLiveData<Boolean?>()

    private lateinit var listaFincas: MutableList<Finca>
    private var _fincasArrayList = mutableListOf<Finca>()
    private lateinit var myAdapter: FincasAdapter

    val resultdoConsulta: LiveData<Boolean?> get() = _rsultRegister


    fun clickResultadoFinca() {
        viewModelScope.launch {
            _rsultRegister.value = withContext(Dispatchers.IO) {
                obtenerFincas()
            }
        }
    }
    private fun obtenerFincas(): Boolean {
        var estado = true
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Fincas").document("Fincas").collection("ActualizacionFinca")
            .whereEqualTo("idUsuario", AsproacaNewAplication.preferencia.obtenerIdUsuario())
            .addSnapshotListener(
                MetadataChanges.INCLUDE,
                object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            estado = false
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                try {
                                    listaFincas = mutableListOf()
                                    listaFincas.add(dc.document.toObject(Finca::class.java))
                                    listaFincas.forEach {
                                        Constantes2.EstadoActualizar = it.estadoActualizar
                                        estado = true
                                    }

                                } catch (e: Exception) {
                                    estado = false
                                }
                            }
                        }
                        _fincasArrayList = mutableListOf()
                        _fincasArrayList.clear()
                        _fincasArrayList.addAll(listaFincas)
                    }
                })
        return estado
    }
}