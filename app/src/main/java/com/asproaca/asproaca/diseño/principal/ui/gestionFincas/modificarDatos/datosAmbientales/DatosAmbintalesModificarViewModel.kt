package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.modificarDatos.datosAmbientales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campo.campocolombiano.design.constantes.Constantes2
import com.campo.campocolombiano.design.constantes.Constantes2.Companion.crearNuevaFinca
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatosAmbintalesModificarViewModel : ViewModel() {
    private var dataBase = Firebase.firestore
    private val _rsultRegister = MutableLiveData<Boolean?>()
    val resultRegister: LiveData<Boolean?> get() = _rsultRegister

    fun clickRegistroFinca() {
        viewModelScope.launch {
            _rsultRegister.value = withContext(Dispatchers.IO) {
                if (crearNuevaFinca == false) {
                    actualizarFincaFinca()
                } else {
                    rearNuevaFincaFinca()
                }
                true
            }
        }
    }

    private fun rearNuevaFincaFinca() {
        dataBase.collection("Fincas").document("Fincas")
            .collection("ActualizacionFinca").document("NuevaFincaPrueba").set(hashMapOf(
                "Hola" to "hola"
            ))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //se creo
                    Log.e("siSeCreo", task.result .toString())
                }else{
                    Log.e("NOSeCreo", task.result .toString())
                }
            }.addOnFailureListener {
                Log.e("ErrorCreacion", it.message.toString())
            }
    }

    private fun actualizarFincaFinca(): Boolean {
        var status = true

        dataBase.collection("Fincas").document("Fincas")
            .collection("ActualizacionFinca").document(Constantes2.idFinca.toString())
            .update(
                mapOf(
                    "idFinca" to Constantes2.idFinca,
                    "nombre_finca" to Constantes2.nombre_finca,
                    "coordenada_x" to Constantes2.coordenada_x,
                    "coordenada_y" to Constantes2.coordenada_y,
                    "vereda_finca" to Constantes2.vereda_finca,
                    "zona" to Constantes2.zona,
                    "municipio" to Constantes2.municipio,
                    "antiguedad_finca" to Constantes2.antiguedad_finca,
                    "historia_finca" to Constantes2.historia_finca,
                    "realiza_quema" to Constantes2.realiza_quema,
                    "creado" to Constantes2.creado,
                    "certificaciones" to Constantes2.certificaciones,
                    "zona_riesgo" to Constantes2.zona_riesgo,
                    "tenencia_de_la_tierra" to Constantes2.tenencia_de_la_tierra,
                    "area_total" to Constantes2.area_total,

                    "datos_casa.servicio_acueducto" to Constantes2.servicio_acueducto,
                    "datos_casa.servicio_alcantarillado" to Constantes2.servicio_alcantarillado,
                    "datos_casa.servicio_electrico" to Constantes2.servicio_electrico,
                    "datos_casa.servicio_internet" to Constantes2.servicio_internet,
                    "datos_casa.tipo_techo" to Constantes2.tipo_techo,
                    "datos_casa.tipo_pared" to Constantes2.tipo_pared,
                    "datos_casa.numero_banios" to Constantes2.numero_banios,
                    "datos_casa.tipo_piso" to Constantes2.tipo_piso,
                    "datos_casa.datos_cocina.tipo_estufa" to Constantes2.tipo_estufa,
                    "datos_casa.datos_cocina.tipo_combustible_alimentos" to Constantes2.tipo_combustible_alimentos,
                    "datos_casa.datos_cocina.tipo_combustible_industriales" to Constantes2.tipo_combustible_industriales,
                    "datos_casa.datos_cocina.fuente_agua_consumo_domestico" to Constantes2.fuente_comsumo_domestico,
                    "datos_casa.datos_cocina.fuente_agua_consumo_industrial" to Constantes2.fuente_comsumo_industrial,
                    "datos_casa.datos_cocina.tratamiento_agua_lavadero" to Constantes2.tratamiento_lavadero,
                    "datos_casa.datos_cocina.tratamiento_agua_lavaplatos" to Constantes2.tratamiento_lavaplatos,
                    "datos_casa.datos_cocina.tratamiento_agua_residual" to Constantes2.tratamiento_agua_residual,
                    "datos_casa.datos_cocina.tratamiento_agua_ducha" to Constantes2.tratamiento_agua_ducha,

                    "datos_sociales.nombre" to Constantes2.nombre,
                    "datos_sociales.identificacion" to Constantes2.identificacion,
                    "datos_sociales.fechaNacimiento" to Constantes2.fechaNacimiento,
                    "datos_sociales.telefono" to Constantes2.telefono,
                    "datos_sociales.correoElectronico" to Constantes2.correoElectronico,
                    "datos_sociales.tipoPoblacion" to Constantes2.tipoPoblacion,
                    "datos_sociales.numeroIntegrantes" to Constantes2.numeroIntegrantes,
                    "datos_sociales.nivelManejoDispositivos" to Constantes2.nivelManejoDispositivos,
                    "datos_sociales.otrosIntegrantes" to Constantes2.listaIntegrantes
                )
            ).addOnCompleteListener { task ->
                status = task.isSuccessful
                Constantes2.idFinca = ""
            }.addOnFailureListener {
                status = false
            }


        return status
    }


}