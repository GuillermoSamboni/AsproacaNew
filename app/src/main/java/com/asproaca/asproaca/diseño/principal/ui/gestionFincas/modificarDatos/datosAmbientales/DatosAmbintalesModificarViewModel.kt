package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.modificarDatos.datosAmbientales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.modelos.*
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.SetOptions
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
                registrarFinca()
            }
        }
    }

    private fun registrarFinca(): Boolean {
        var status = true

        val datos_cocina = CocinaCasa(
            Constantes2.tipo_estufa,
            Constantes2.tipo_combustible_alimentos,
            Constantes2.tipo_combustible_industriales,
            Constantes2.fuente_comsumo_domestico,
            Constantes2.fuente_comsumo_industrial,
            Constantes2.tratamiento_agua_ducha,
            Constantes2.tratamiento_lavadero,
            Constantes2.tratamiento_lavaplatos,
            Constantes2.tratamiento_agua_residual
        )

        val datos_casa = Casa(
            Constantes2.servicio_acueducto,
            Constantes2.servicio_alcantarillado,
            Constantes2.servicio_electrico,
            Constantes2.servicio_internet,
            Constantes2.tipo_techo,
            Constantes2.tipo_pared,
            Constantes2.numero_banios,
            Constantes2.tipo_piso,
            datos_cocina
        )


        val datosTrabajadores = DatosTrabajadorres(
            Constantes2.cantidad_trabajadores_contratados,
            Constantes2.tipo_contratacion,
            Constantes2.cant_pago_dinero,
            Constantes2.cant_pago_especie,
            Constantes2.horario_laboral,
            Constantes2.descripcion_adicional,
            Constantes2.alojamiento_trabajadores,
            Constantes2.estado_alojamiento_trabajadores,
        )


        val datosAmbientales = DatosAmbientales(
            Constantes2.tiene_ecosistema_acuaticos_naturales,
            Constantes2.ecosistemas_naturales_acuaticos,
            Constantes2.tiene_ecosistema_acuaticos_artificiales,
            Constantes2.ecosistemas_artificial_acuaticos,
            Constantes2.tiene_ecositemas_terrestes_naturales,
            Constantes2.ecosistemas_terrestre_natural,
            Constantes2.area_ecosistemas_terrestre_natural,
            Constantes2.consesion_agua,
            Constantes2.tipo_arboles_descripcion,
            Constantes2.tratamiento_basura,
            Constantes2.separacion_basura,
            Constantes2.cobertura_suelos,
            Constantes2.areas_con_erosion,
            Constantes2.areas_con_evidencias_remocion,
            Constantes2.animales_silvestres_en_cautivero,
            Constantes2.captacion_agua_lluvia,
        )

        val datosSociales = DatosSociales(
            Constantes2.nombre,
            Constantes2.identificacion,
            Constantes2.fechaNacimiento,
            Constantes2.telefono,
            Constantes2.correoElectronico,
            Constantes2.tipoPoblacion,
            Constantes2.numeroIntegrantes,
            Constantes2.nivelManejoDispositivos,
            Constantes2.listaIntegrantes,
        )
        val datos_finca = Finca(
            Constantes2.nombre_finca,
            Constantes2.idUsuario,
            Constantes2.encargadoRegistro,
            false,
            Constantes2.nombre_finca,
            Constantes2.coordenada_x,
            Constantes2.coordenada_y,
            Constantes2.vereda_finca,
            Constantes2.zona,
            Constantes2.antiguedad_finca,
            Constantes2.historia_finca,
            Constantes2.realiza_quema,
            Constantes2.creado,
            Constantes2.certificaciones,
            Constantes2.zona_riesgo,
            Constantes2.tenencia_de_la_tierra,
            Constantes2.area_total,
            datos_casa,
            datosSociales,
            Constantes2.listaProductivos,
            Constantes2.listaAnimales,
            datosTrabajadores,
            datosAmbientales,
        )
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
                    "antiguedad_finca" to Constantes2.antiguedad_finca,
                    "historia_finca" to Constantes2.historia_finca,
                    "realiza_quema" to Constantes2.realiza_quema,
                    "creado" to Constantes2.creado,
                    "certificaciones" to Constantes2.certificaciones,
                    "zona_riesgo" to Constantes2.zona_riesgo,
                    "tenencia_de_la_tierra" to Constantes2.tenencia_de_la_tierra,
                    "area_total" to Constantes2.area_total,
                    //"datos_ambientales.animales_silvestres_en_cautivero" to "holaaa",

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
                Log.e("ASASDF","SuccessFull")
                Constantes2.idFinca = ""
            }.addOnFailureListener {
                Log.e("ASASDF","Failed Ecxecute")
                status = false
            }


        return status
    }


}