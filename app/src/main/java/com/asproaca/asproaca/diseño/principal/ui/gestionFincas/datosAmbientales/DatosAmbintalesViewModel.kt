package com.asproaca.asproaca.diseño.principal.ui.gestionFincas.datosAmbientales

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

class DatosAmbintalesViewModel : ViewModel() {
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
            Constantes2.genero,
            Constantes2.nivelAcademico,
            Constantes2.numeroIntegrantes,
            Constantes2.nivelAcademico,
            Constantes2.listaIntegrantes
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
            Constantes2.cantidad_viviendas,
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
        dataBase.collection("Fincas").document(Constantes2.nombre_finca.toString())
            .set(datos_finca, SetOptions.merge()).addOnCompleteListener { task ->
                status = task.isSuccessful
            }.addOnFailureListener {
                status = false
            }


        return status
    }


}