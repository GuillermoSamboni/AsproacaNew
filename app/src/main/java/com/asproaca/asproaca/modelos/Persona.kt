package com.asproaca.asproaca.modelos

data class Persona(
    var nombre_completo: String? = null,
    var fecha_nacimiento: String? = null,
    var identificacion: String? = null,
    var lugar_expedicion: String? = null,
    val tipo_persona: String? = null,
    var telefono_principal: String? = null,
    var telefono_secundario: String? = null,
    var correo_electronico: String? = null,
    var actualmente_estudia: String? = null,
    var discapacidad: String? = null,
    var nombres_discapacidad: String? = null,
    var estado: String? = null,
    var tipo_poblacion: String? = null,
    var nivel_estudio: String? = null,
    var sabe_leer: String? = null,
    var sabe_escribir: String? = null,
    var nivel_manejo_dispositivos: String? = null,
    var maneja_redes_sociales: String? = null,
    var redes_sociales: String? = null,
)
